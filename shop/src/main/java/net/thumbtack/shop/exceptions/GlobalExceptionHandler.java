package net.thumbtack.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ErrorCode errorCode = Arrays.stream(ErrorCode.values()).filter(e -> e.getField().equals(fieldError.getField()) && e.name().startsWith("WRONG")).findFirst().get();
            errorResponse.addError(new ErrorResponseItem(errorCode));
        });
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CarShopException.class)
    @ResponseBody
    public ErrorResponse handleError(CarShopException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new ErrorResponseItem(ex));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadable() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new ErrorResponseItem(ErrorCode.WRONG_OR_EMPTY_REQUEST));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            IllegalArgumentException.class})
    @ResponseBody
    public ErrorResponse handleMissingServletRequest() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new ErrorResponseItem(ErrorCode.WRONG_OR_EMPTY_REQUEST));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ErrorResponse handleNoHandlerFound() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new ErrorResponseItem(ErrorCode.WRONG_URL));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(Throwable ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new ErrorResponseItem(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
        return errorResponse;
    }

}
