package com.g.commons.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.g.commons.exception.GenericApiException;
import com.g.commons.exception.IllegalArgument;
import com.g.commons.model.RestApiResponse;

/**
 * 处理异常公共方法
 *
 * @author zhongsh
 * @version 2016/9/29
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptionEx(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof GenericApiException) {
            // 自定义异常
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            GenericApiException gae = (GenericApiException) ex;
            RestApiResponse response = new RestApiResponse(gae.getCode(), gae.getMessage());
            return handleExceptionInternal(ex, response, headers, status, request);
        } else {
            // 未知异常
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = RestApiResponse.error(ex.getMessage());
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * @Valid，@Validated校验，参数绑定出错
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = getErrorMessage(bindingResult);
        RestApiResponse response = new RestApiResponse(IllegalArgument.CODE, "错误的输入参数：" + errorMessage);
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    /**
     * 处理数据绑定出错信息
     *
     * @param bindingResults
     */
    public void bindingResultHandler(BindingResult... bindingResults) {
        if (bindingResults == null || bindingResults.length == 0) {
            return;
        }
        for (BindingResult bindingResult : bindingResults) {
            String errorMessage = getErrorMessage(bindingResult);
            if (errorMessage != null) {
                if (StringUtils.isNotBlank(errorMessage)) {
                    throw new IllegalArgument(errorMessage);
                } else {
                    throw new IllegalArgument();
                }
            }
        }
    }

    /**
     * 从BindingResult获取错误信息
     *
     * @param bindingResult
     * @return 错误信息，如果返回null表示没有错误
     */
    private String getErrorMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return null;
        }
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors != null && allErrors.size() > 0) {
            ObjectError objectError = allErrors.get(0);
            String defaultMessage = objectError.getDefaultMessage();
            if (StringUtils.isNotBlank(defaultMessage)) {
                return defaultMessage;
            }
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return fieldError.getField() + "[" + fieldError.getRejectedValue().toString() + "]";
            }
            return "";
        }
        return null;
    }
}
