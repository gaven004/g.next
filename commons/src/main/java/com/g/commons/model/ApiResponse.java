package com.g.commons.model;

public class ApiResponse<T> {
    private String result;
    private String message;

    private T body;

    public ApiResponse() {
        super();
    }

    public ApiResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ApiResponse(String result, String message, T body) {
        this.result = result;
        this.message = message;
        this.body = body;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<T>("SUCCESS", "成功");
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<T>("SUCCESS", message);
    }

    public static <T> ApiResponse<T> success(String message, T body) {
        return new ApiResponse<T>("SUCCESS", message, body);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<T>("ERROR", "失败");
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<T>("ERROR", message);
    }

    public static <T> ApiResponse<T> error(String message, T body) {
        return new ApiResponse<T>("ERROR", message, body);
    }

    public static <T> ApiResponse<T> create(boolean result) {
        return result ? success() : error();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RestApiResponse [result=");
        builder.append(result);
        builder.append(", message=");
        builder.append(message);
        builder.append(", body=");
        builder.append(body);
        builder.append("]");
        return builder.toString();
    }
}
