package shop.shopping.exception;

import lombok.Getter;
import shop.shopping.constant.ErrorCode;
@Getter
public class TokenNotFoundException extends RuntimeException{

    private ErrorCode errorCode;

    public TokenNotFoundException(String message , ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;

    }
}
