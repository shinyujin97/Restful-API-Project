package shop.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsernameLoginResponseDto {

    String username;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
