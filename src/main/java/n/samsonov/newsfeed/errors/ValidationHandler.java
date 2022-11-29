package n.samsonov.newsfeed.errors;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import n.samsonov.newsfeed.dto.CustomSuccessResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<Integer> errors = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((anyError) -> {
                    String errorMessage = anyError.getDefaultMessage();
                    errors.add(ErrorEnum.getCodes(errorMessage).getCode());
                });
        return new ResponseEntity<>(new CustomSuccessResponse<>()
                .setStatusCode(errors
                        .stream()
                        .sorted().findFirst().get())
                .setSuccess(true)
                .setCodes(errors
                        .stream()
                        .sorted()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<Integer> errors = new ArrayList<>();
        for (ConstraintViolation<?> someError : ex.getConstraintViolations()) {
            String errorMessage = someError.getMessage();
            errors.add(ErrorEnum.getCodes(errorMessage).getCode());
        }
        return new ResponseEntity<>(new CustomSuccessResponse<>()
                .setStatusCode(errors.get(0))
                .setSuccess(true)
                .setCodes(errors.stream().distinct().sorted()), HttpStatus.BAD_REQUEST
        );
    }
}

