package shop.shopping.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
    JWT Token 발급
    장점 : 서버는 비밀키만 알고 있으면 되기 때문에 세션 방식과 같이 별도의 인증 저장소가 필요하지 않음 => 서버측 부하 감소
           여러개의 서버를 사용하는 대형 서비스 같은 경우에 접근 권한 관리가 매우 효율적임 => 확장성이 좋음
           Refresh Token까지 활용한다면 더 높은 보안성을 가질 수 있음

    단점 : Payload의 정보(Claim)가 많아질 수록 토큰이 커짐
           중요한 데이터는 넣을 수 없음
           토큰 자체를 탈취당하면 대처가 어려움
           로그아웃 시 JWT 방식은 세션이 없는 stateless 방식이기 때문에 토큰 관리가 어려움
 */

// 토큰을 생성하고 검증하는 클래스
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider{

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    @Value("${jwt.token.valid.time}")
    private Long tokenValidTime;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.refresh-token-valid-time}")
    private Long refreshTokenValidTime;


    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @PostConstruct
    protected void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // 토큰 생성
    public String createToken(String username){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("username", username);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails =customUserDetailsService.loadUserByUsername(getNickname(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }


    public String getNickname(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    //토큰 유효성, 만료일자 확인
    public boolean validateTokenExpiration(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰: {}", e.getMessage());
            // 만료된 토큰 처리 (예: 리프레시 토큰)
            return false;
        } catch (SignatureException e) {
            log.error("잘못된 토큰 서명: {}", e.getMessage());
            // 잘못된 서명 처리
            return false;
        } catch (MalformedJwtException e) {
            log.error("잘못된 토큰 형식: {}", e.getMessage());
            // 잘못된 토큰 형식 처리
            return false;
        } catch (JwtException e) {
            log.error("JWT 예외: {}", e.getMessage());
            // 기타 JWT 예외 처리
            return false;
        } catch (Exception e) {
            log.error("예기치 않은 오류: {}", e.getMessage());
            // 예기치 않은 오류 처리
            return false;
        }
    }
    // 토큰 얻어오기

    public String getTokenFromHeader(HttpServletRequest request){

        return request.getHeader(tokenHeader); // -> Authorization <- application.properties
    }
}