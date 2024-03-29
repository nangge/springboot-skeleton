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

    SECKILL_NOT_EXISTS("20001", "秒杀活动不存在或已结束"),
    SECKILL_HAD_JOIN("20002", "您已参加过该秒杀活动"),
    SECKILL_STOCK_NOT_ENOUGH("20003", "库存不足"),


    FOLDER_NOT_EXISTS("30001", "目录不存在"),
    FILE_NOT_EXISTS("30002", "文件不存在"),

    ;


    private String code;
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }


}
