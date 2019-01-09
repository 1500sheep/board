package com.board.security;

import com.board.exception.NotAllowedException;
import com.board.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import utils.RestResponse;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public RestResponse emptyResultData(EntityNotFoundException exception) {
        log.error("object error : {}", exception.getMessage());
        return RestResponse.error(exception.getMessage()).build();
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public RestResponse unAuthentication(UnAuthenticationException exception){
        log.error("object error : {}", exception.getMessage());
        return RestResponse.error(exception.getMessage()).build();
    }

    @ExceptionHandler(NotAllowedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public RestResponse notAllowed(NotAllowedException exception) {
        log.error("object error : {}", exception.getMessage());
        return RestResponse.error(exception.getField(), exception.getMessage()).build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestResponse<?> methodArgumentValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        RestResponse.ErrorResponseBuilder errorResponseBuilder = RestResponse.error();
        for (ObjectError objectError : errors) {
            log.error("object error : {}", objectError);
            FieldError fieldError = (FieldError) objectError;
            errorResponseBuilder.appendError(fieldError.getField(), getErrorMessage(fieldError));
        }
        return errorResponseBuilder.build();
    }

    private String getErrorMessage(FieldError fieldError) {
        Optional<String> code = getFirstCode(fieldError);
        if (!code.isPresent()) {
            return null;
        }

        String errorMessage = messageSourceAccessor.getMessage(code.get(), fieldError.getArguments(), fieldError.getDefaultMessage());
        log.info("error message: {}", errorMessage);
        return errorMessage;
    }

    private Optional<String> getFirstCode(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        if (codes == null || codes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(codes[0]);
    }
}
