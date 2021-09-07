package com.nango.skeletonweb.infrastructure.exception;

import lombok.Getter;

/**
 * @Author wyn
 * @Date 2021/3/1
 * @Description 错误码枚举类
 */
@Getter
public enum ErrorCodeEnum {
    /**
     * 成功
     */
    SUCCESS("0", "ok"),
    /**
     * 失败
     */
    FAILED("10000", "系统繁忙，请稍后再试~"),
    LOGIN_FAILED("10001", "登录失败"),
    BIZ_FAILED("10002", "请求异常,请稍后重试！"),
    NOT_DATA_PERMISSION("10003", "没有操作该数据的权限,请联系管理员"),
    PARAM_ERROR("10004","参数格式异常"),
    PARAM_TYPE_ERROR("10005","参数类型错误"),
    SYSTEM_ERROR("10006","系统发生错误"),
    NOT_SUPPORTED_REQ_METHOD("10006","请求方式错误"),
    ;


    private String code;
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }


}
