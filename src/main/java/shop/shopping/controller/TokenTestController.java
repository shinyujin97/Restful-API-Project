package shop.shopping.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 토큰 권한 에 대한 테스트 컨트롤러
 */
@RestController
public class TokenTestController {

    @PostMapping("/test")
    public String tokenTest(){
        return "<h1>test 통과<h1>";
    }
}
