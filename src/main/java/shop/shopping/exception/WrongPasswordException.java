package shop.shopping.exception;

import lombok.Getter;
import shop.shopping.constant.ErrorCode;
@Getter
public class WrongPasswordException extends RuntimeException{

    private ErrorCode errorCode;

    public WrongPasswordException(String message , ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
