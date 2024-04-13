package shop.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsernameLoginResponseDto {

    String nickname;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
