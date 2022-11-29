package n.samsonov.newsfeed.errors;

import n.samsonov.newsfeed.dto.CustomSuccessResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class ApplicationExeptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> CustomSuccessResponse<T> customException(CustomException ex) {
        String msg = ex.getAllErrors().getMessage();
        Integer code = ErrorEnum.getCodes(msg).getCode();
        return CustomSuccessResponse.handleException(new Integer[]{code});
    }

    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<CustomSuccessResponse> customEx(NullPointerException exception) {
        return new ResponseEntity<>
                (CustomSuccessResponse.handleNullException(ValidationConstants.UNAUTHORISED), HttpStatus.UNAUTHORIZED);
    }
}
