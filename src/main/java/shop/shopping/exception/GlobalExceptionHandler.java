package shop.shopping.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.shopping.constant.ErrorCode;


/*
    에러 코드 정의하기
 */
@Slf4j
@RestControllerAdvice // 예외 처리 어노테이션
public class GlobalExceptionHandler {

    // 요청이 들어왔을 때 에러가 나면 ExceptionHandler 들어와 에러 처리

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundMemberException(NotFoundMemberException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundTokenException(TokenNotFoundException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPasswordException(WrongPasswordException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.ERROR_PASSWORD);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(DuplicatedMemberUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedMemberUsernameException(DuplicatedMemberUsernameException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATED_MEMBER_USERNAME);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(DuplicatedMemberNicknameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedMemberNicknameException(DuplicatedMemberNicknameException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATED_MEMBER_NICKNAME);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }


}
