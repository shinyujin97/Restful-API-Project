package shop.shopping.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    MEMBER_NOT_FOUND(404,"MEMBER-ERR-404","존재하지 않는 유저입니다"),
    ERROR_PASSWORD(401,"WRONG-PW-ERR-401","잘못된 비밀번호 입니다"),
    DUPLICATED_MEMBER_USERNAME(400,"DUPLICATED-ERR","중복된 아이디 입니다"),
    DUPLICATED_MEMBER_NICKNAME(400,"DUPLICATED-ERR","중복된 닉네임 입니다"),
    INVALID_TOKEN(401,"INVALID-TOKEN-ERR-401","TOKEN NOT FOUND ERROR");

    private int status;
    private String errorCode;
    private String message;
}
