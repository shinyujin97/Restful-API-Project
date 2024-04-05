package shop.shopping.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shop.shopping.constant.ErrorCode;
import shop.shopping.dto.MemberJoinRequestDto;
import shop.shopping.dto.MemberLoginRequestDto;
import shop.shopping.dto.UsernameLoginResponseDto;
import shop.shopping.entity.Member;
import shop.shopping.exception.DuplicatedMemberNicknameException;
import shop.shopping.exception.DuplicatedMemberUsernameException;
import shop.shopping.exception.NotFoundMemberException;
import shop.shopping.exception.WrongPasswordException;
import shop.shopping.jwt.JwtTokenProvider;
import shop.shopping.repository.MemberRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
   public Member joinMember(MemberJoinRequestDto joinDto){
        String password = passwordEncoder.encode(joinDto.getPassword());
        Member member = new Member(joinDto.getUsername() , password , joinDto.getNickname() , joinDto.getGender());
        log.info("selectedPw : {} pw : {}",joinDto.getPassword(), password );
        return memberRepository.save(member);
   }

   @Transactional
   public void processNewMember(MemberJoinRequestDto joinDto){
    if (memberRepository.existsByUsername(joinDto.getUsername())) {
        throw new DuplicatedMemberUsernameException("중복된 아이디 입니다", ErrorCode.DUPLICATED_MEMBER_NICKNAME);
    } else if (memberRepository.existsByNickname(joinDto.getNickname())) {
        throw new DuplicatedMemberNicknameException("중복된 닉네임 입니다", ErrorCode.DUPLICATED_MEMBER_NICKNAME);
    }else {
        Member member = joinMember(joinDto);
    }
   }

   @Transactional
    public UsernameLoginResponseDto loginByUsername(MemberLoginRequestDto loginDto){
        Member member = memberRepository.findByUsername((loginDto.getUsername()));

        if (member == null){
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        }
        else if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호 입니다", ErrorCode.ERROR_PASSWORD);
        }
        else {
            // 리프레시 토큰을 생성하고 멤버 정보를 업데이트
            String refreshToken = jwtTokenProvider.createRefreshToken();
            member.updateRefreshToken(refreshToken);

            // 로그인 정보를 포함한 DTO를 생성하여 반환
            return new UsernameLoginResponseDto(member.getUsername(), jwtTokenProvider.createToken(member.getNickname()), member.getRefreshToken());
        }

   }


}
