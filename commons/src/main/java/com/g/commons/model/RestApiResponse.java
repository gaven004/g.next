package com.g.commons.model;

public class RestApiResponse<T> {
    private String result;
    private String message;

    private T body;

    public RestApiResponse() {
        super();
    }

    public RestApiResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public RestApiResponse(String result, String message, T body) {
        this.result = result;
        this.message = message;
        this.body = body;
    }

    public static <T> RestApiResponse<T> success() {
        return new RestApiResponse<T>("SUCCESS", "成功");
    }

    public static <T> RestApiResponse<T> success(String message) {
        return new RestApiResponse<T>("SUCCESS", message);
    }
    
    public static <T> RestApiResponse<T> success(String message, T body) {
        return new RestApiResponse<T>("SUCCESS", message, body);
    }
    
    public static <T> RestApiResponse<T> error() {
        return new RestApiResponse<T>("ERROR", "失败");
    }
    
    public static <T> RestApiResponse<T> error(String message) {
        return new RestApiResponse<T>("ERROR", message);
    }
    
    public static <T> RestApiResponse<T> error(String message, T body) {
        return new RestApiResponse<T>("ERROR", message, body);
    }
    
    public static <T> RestApiResponse<T> create(boolean result) {
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
