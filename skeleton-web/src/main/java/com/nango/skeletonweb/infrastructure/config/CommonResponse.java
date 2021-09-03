package com.nango.skeletonweb.infrastructure.config;

import lombok.Data;

/**
 * @Author wyn
 * @Date 2021/3/1
 * @Description 公共返回对象
 */
@Data
public class CommonResponse<T> {
    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private boolean success=false;

    private T data;


    public CommonResponse(T data, String code, String msg, boolean success) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public static CommonResponse<Void> success() {
        return new CommonResponse<>(null, "0", "成功", true);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(data, "0", "成功", true);
    }

    public static CommonResponse<String> failed(String code, String msg) {
        return new CommonResponse<>(null, code, msg, false);
    }
}
