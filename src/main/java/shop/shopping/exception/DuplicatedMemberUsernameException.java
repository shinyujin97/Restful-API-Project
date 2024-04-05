package shop.shopping.exception;

import lombok.Getter;
import shop.shopping.constant.ErrorCode;

@Getter
public class DuplicatedMemberUsernameException extends RuntimeException{

    private ErrorCode errorCode;

    public DuplicatedMemberUsernameException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
