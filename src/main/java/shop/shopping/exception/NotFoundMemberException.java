package shop.shopping.exception;

import lombok.Getter;
import shop.shopping.constant.ErrorCode;

@Getter
public class NotFoundMemberException extends RuntimeException{

    private ErrorCode errorCode;

    public NotFoundMemberException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
