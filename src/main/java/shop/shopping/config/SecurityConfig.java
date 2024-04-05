package shop.shopping.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.shopping.jwt.JwtAuthenticationFilter;
import shop.shopping.jwt.JwtTokenProvider;
import shop.shopping.service.MemberService;

//@Configuration // Bean을 등록할 때 싱글톤(Singleton)이 되도록 보장 스프링 컨테이너에서 Bean을 관리할 수 있게 됨
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않음 Cross Site Request Forgery
        http.cors().disable(); // STATELESS 라 cors는 disable
        http.httpBasic().disable() // 일반적인 루트가 아닌 다른 방식으로 요청시 거절, header에 id, pw가 아닌 token(jwt)을 달고 간다. 그래서 basic이 아닌 bearer를 사용한다.


                .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                // antMatchers = 특정 리소스에 대해서 권한 설정
                .antMatchers("/api/v1/members/join" , "/api/v1/members/login", "/api/v1/boards/main").permitAll() // 회원가입 , 로그인 인증절차 없이 허용가능
                .antMatchers("/**").authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
