package com.nango.skeletonweb.infrastructure.exception;

import com.nango.skeletonweb.infrastructure.config.CommonResponse;
import lombok.Data;

/**
 * @Author wyn
 * @Date 2021/09/02
 * @Description 业务异常类
 */
@Data
public class BizException extends RuntimeException {
    private final String code;
    private final String msg;

    public BizException(String... codes) {
        this.code = codes[0];
        this.msg = this.getMessage();
    }

    public BizException(CommonResponse res) {
        super(res.getMsg());
        this.code = res.getCode();
        this.msg = res.getMsg();
    }


    public BizException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
