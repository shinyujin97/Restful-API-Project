package shop.shopping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.shopping.constant.ErrorCode;
import shop.shopping.dto.TokenDto;
import shop.shopping.entity.Member;
import shop.shopping.exception.NotFoundMemberException;
import shop.shopping.jwt.JwtTokenProvider;
import shop.shopping.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public String getTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        log.info("Header token : {}" ,token);
        return token;
    }

    @Transactional
    public TokenDto reIssueToken(TokenDto tokenDto) {

        Member member = memberRepository.findByRefreshToken(tokenDto.getJwtRefreshToken());
        if(member==null){
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        }
        String accessToken = jwtTokenProvider.createToken(member.getNickname());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(refreshToken);
        return new TokenDto(accessToken, refreshToken);
    }
}
