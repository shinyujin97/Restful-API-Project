package shop.shopping.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
/*
    JWT Token 발급
    장점 : 서버는 비밀키만 알고 있으면 되기 때문에 세션 방식과 같이 별도의 인증 저장소가 필요하지 않음 => 서버측 부하 감소
           여러개의 서버를 사용하는 대형 서비스 같은 경우에 접근 권한 관리가 매우 효율적임 => 확장성이 좋음
           Refresh Token까지 활용한다면 더 높은 보안성을 가질 수 있음

    단점 : Payload의 정보(Claim)가 많아질 수록 토큰이 커짐
           중요한 데이터는 넣을 수 없음
           토큰 자체를 탈취당하면 대처가 어려움
           로그아웃 시 JWT 방식은 세션이 없는 stateless 방식이기 때문에 토큰 관리가 어려움
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private CustomUserDetailsService customUserDetailsService;
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token-valid-time}")
    private Long tokenValidTime;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.refresh-token-valid-time}")
    private Long refreshTokenValidTime;


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails =customUserDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }


    // 토큰에서 회원 정보 추출
    public String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public boolean validateTokenExpiration(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch(ExpiredJwtException e) {
            log.error("만료된 토큰입니다");
            return false;
        } catch (Exception e) {
            log.error("Failed to validate token",e);
            return false;
        }
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    // Authorization <- application.properties 에 설정해 놨음
    public String getTokenFromHeader(HttpServletRequest request){

        return request.getHeader(tokenHeader);
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }


    public String createToken(String username){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("username" , username); // 정보는 key / value 쌍으로 저장된다.

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256,secretKey) // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

}
