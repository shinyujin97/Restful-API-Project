package shop.shopping.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.shopping.dto.MemberJoinRequestDto;
import shop.shopping.dto.MemberLoginRequestDto;
import shop.shopping.dto.UsernameLoginResponseDto;
import shop.shopping.service.MemberService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/members")
@RequiredArgsConstructor // final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 생성
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid MemberJoinRequestDto joinDto , Errors errors) {
        Map result = new HashMap<String,String>();
        if (errors.hasErrors()){
            result.put("reslut","회원가입 실패");
            return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
        }else {
            memberService.processNewMember(joinDto);
            result.put("reslut","회원가입 성공");
            return new ResponseEntity(result,HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity emailLogin(@RequestBody @Valid MemberLoginRequestDto loginDto, Errors errors){
        Map result = new HashMap<String,String>();
        if(errors.hasErrors()){
            result.put("result","로그인 실패");
            return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
        }
        UsernameLoginResponseDto response = memberService.loginByUsername(loginDto);
        result.put("result" , "로그인 성공");
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
