package shop.shopping.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.shopping.constant.ErrorCode;
import shop.shopping.entity.Member;
import shop.shopping.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if (member == null){
            throw new UsernameNotFoundException("해당 유저는 없습니다");
        }
        log.info("member : {} ", member);
        return new CustomUserDetails(member);
    }
}
