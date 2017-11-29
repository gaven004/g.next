package com.g.commons.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.g.commons.exception.GenericAppException;
import com.g.commons.model.RestApiResponse;

/**
 * 全局配置
 *
 * @author zhongsh
 * @version 2017/1/11
 */
@ControllerAdvice
public class GlobalControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    /**
     * GenericAppException异常处理
     *
     * @param exception
     * @return 返回JSON
     */
    @ExceptionHandler(GenericAppException.class)
    @ResponseBody
    public RestApiResponse<?> generalSystemErrorHandler(GenericAppException exception) {
        logger.warn(exception.getDetail(), exception);
        return RestApiResponse.error(exception.getMessage());
    }

    /**
     * 其它异常处理
     *
     * @param exception
     * @return 返回JSON
     */
    @ExceptionHandler
    @ResponseBody
    public RestApiResponse<?> exceptionHandler(Exception exception) {
        logger.error("操作失败", exception);
        return RestApiResponse.error("操作失败，" + exception.getMessage());
    }
}