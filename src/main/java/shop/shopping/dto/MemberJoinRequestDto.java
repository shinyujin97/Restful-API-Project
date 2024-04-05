package shop.shopping.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/*
회원가입 post dto
 */
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@Getter
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
@Data
public class MemberJoinRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String username;
    @NotBlank(message = "패스워드 입력해주세요")
    private String password;
    @NotBlank(message = "닉네임 입력해주세요")
    private String nickname;
    @NotBlank(message = "성별을 선택해주세요")
    private String gender;

}
