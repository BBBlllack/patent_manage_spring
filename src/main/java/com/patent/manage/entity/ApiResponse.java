package com.patent.manage.entity;

import lombok.Data;

import java.util.Map;

/**
 * 前后端交互体
 * @param <T>
 */
@Data
public class ApiResponse<T> {
    private Integer code;
    private T data;
    private String msg;
    private Map<T, T> map;

    private static Integer SUCCESS = 1;
    private static Integer ERROR = 0;

    public static <T> ApiResponse<T> success(Integer code, T data, String msg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = code;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> success(T data, String msg, Map map) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = SUCCESS;
        response.msg = msg;
        response.data = data;
        response.map = map;
        return response;
    }

    public static <T> ApiResponse<T> success(T data, String msg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = SUCCESS;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = SUCCESS;
        response.msg = "success";
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> error(int code, String msg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = code;
        response.msg = msg;
        response.data = null;
        return response;
    }

    public static <T> ApiResponse<T> error( String msg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = ERROR;
        response.msg = msg;
        response.data = null;
        return response;
    }

}
