package com.g.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.g.commons.exception.ErrorCode;
import com.g.commons.exception.GenericAppException;
import com.g.commons.model.ApiResponse;

@ControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(GenericAppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<?> generalSystemErrorHandler(GenericAppException exception) {
        log.warn(exception.getDetail(), exception);
        return ApiResponse.error(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder buff = new StringBuilder("输入参数校验失败：[");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            buff.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        buff.replace(buff.length() - 2, buff.length(), "]");
        return new ApiResponse(ErrorCode.IllegalArgument, buff.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<?> exceptionHandler(Exception exception) {
        log.error("操作失败", exception);
        return ApiResponse.error("操作失败，" + exception.getMessage());
    }
}
