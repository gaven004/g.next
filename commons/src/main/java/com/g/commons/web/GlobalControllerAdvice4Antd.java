package com.g.commons.web;

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
import com.g.commons.model.AntdResponse;

@ControllerAdvice
public class GlobalControllerAdvice4Antd {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice4Antd.class);

    @ExceptionHandler(GenericAppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public AntdResponse<?> generalSystemErrorHandler(GenericAppException exception) {
        log.warn(exception.getDetail(), exception);
        return AntdResponse.error(exception.getCode(), exception.getMessage(), exception.getSn());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AntdResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder buff = new StringBuilder("输入参数校验失败：[");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            buff.append("属性").append(fieldError.getField()).append("：")
                    .append(fieldError.getDefaultMessage()).append(", ");
        }
        buff.replace(buff.length() - 2, buff.length(), "]");
        return AntdResponse.error(ErrorCode.IllegalArgument, buff.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public AntdResponse<?> exceptionHandler(Exception exception) {
        log.error("操作失败", exception);
        return AntdResponse.error(ErrorCode.Generic, exception.getMessage());
    }
}
