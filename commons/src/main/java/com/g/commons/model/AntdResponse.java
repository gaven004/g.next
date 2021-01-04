package com.g.commons.model;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.ToString;

/**
 * 按 Ant Design Pro 的接口规范封装
 * <p>
 * 参考：https://pro.ant.design/docs/request-cn#%E7%BB%9F%E4%B8%80%E6%8E%A5%E5%8F%A3%E8%A7%84%E8%8C%83
 */
@Data
@ToString
public class AntdResponse<T> {
    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String traceId;
    private T data;
    private Integer current;
    private Integer pageSize;
    private Long total;

    public static <T> AntdResponse<T> success() {
        AntdResponse<T> response = new AntdResponse();
        response.success = true;
        return response;
    }

    public static <T> AntdResponse<T> success(T data) {
        AntdResponse<T> response = new AntdResponse();
        response.success = true;
        response.data = data;
        return response;
    }

    public static <T> AntdResponse<T> success(Page page) {
        AntdResponse<T> response = new AntdResponse();
        response.success = true;
        response.data = (T) page.getContent();
        response.current = page.getNumber() + 1;
        response.pageSize = page.getSize();
        response.total = page.getTotalElements();
        return response;
    }

    public static <T> AntdResponse<T> error(String code, String message) {
        AntdResponse<T> response = new AntdResponse();
        response.success = false;
        response.errorCode = code;
        response.errorMessage = message;
        return response;
    }

    public static <T> AntdResponse<T> error(String code, String message, String traceId) {
        AntdResponse<T> response = new AntdResponse();
        response.success = false;
        response.errorCode = code;
        response.errorMessage = message;
        response.traceId = traceId;
        return response;
    }
}
