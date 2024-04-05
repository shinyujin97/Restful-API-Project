package shop.shopping.exception;

import lombok.Getter;
import shop.shopping.constant.ErrorCode;

@Getter
public class DuplicatedMemberNicknameException extends RuntimeException{

    private ErrorCode errorCode;

    public DuplicatedMemberNicknameException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
