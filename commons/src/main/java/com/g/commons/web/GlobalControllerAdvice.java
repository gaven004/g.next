package com.g.commons.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@ControllerAdvice(basePackages = "com.g")
public class GlobalControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder buff = new StringBuilder("输入参数校验失败：[");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            buff.append("属性").append(fieldError.getField()).append("：")
                    .append(fieldError.getDefaultMessage()).append(", ");
        }
        buff.replace(buff.length() - 2, buff.length(), "]");
        return new ApiResponse(ErrorCode.IllegalArgument, buff.toString());
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> exceptionHandler(Exception ex) {
        if (ex instanceof GenericAppException) {
            log.warn(((GenericAppException) ex).getDetail(), ex);
        } else {
            log.error("操作失败", ex);
        }

        Optional<ResponseStatus> status = Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class));
        return response(status.map(s -> s.value()).orElse(null), null, ApiResponse.error(ex.getMessage()));
    }

    private static <T> ResponseEntity<T> response(HttpStatus status, HttpHeaders headers) {
        return response(status, headers, null);
    }

    private static <T> ResponseEntity<T> response(HttpStatus status, HttpHeaders headers, T body) {
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (headers == null) {
            headers = new HttpHeaders();
        }

        return new ResponseEntity<T>(body, headers, status);
    }
}
