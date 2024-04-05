package shop.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
EncoderConfig class 와 SecurityConfig class 랑은 꼭 별개로 해야 한다
이유는 순환참조에서 에러가 날 수 가 있기 때문이다
 */
@Configuration // Bean을 등록할 때 싱글톤(Singleton)이 되도록 보장 스프링 컨테이너에서 Bean을 관리할 수 있게 됨
public class EncoderConfig {

    // 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
