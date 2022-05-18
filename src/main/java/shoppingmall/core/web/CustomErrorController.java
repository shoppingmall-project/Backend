package shoppingmall.core.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shoppingmall.core.web.dto.ResponseDto;

@Slf4j
@RestControllerAdvice
public class CustomErrorController {

    //    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDto illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL");
    }

    @ExceptionHandler
    public ResponseDto RuntimeExHandler(RuntimeException e) {
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL");
    }

    //    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseDto exHandler(Exception e) {
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL");
    }
}
