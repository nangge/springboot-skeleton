package com.nango.skeletonweb.infrastructure.config;

import cn.hutool.core.util.NumberUtil;
import com.nango.skeletonweb.infrastructure.exception.BizException;
import com.nango.skeletonweb.infrastructure.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @Author wyn
 * @Date 2021/3/1
 * @Description 全局异常拦截
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理全局业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public CommonResponse handleBizException(BizException exception) {
        log.warn("业务异常:{}",exception );
        return CommonResponse.failed(exception.getCode(), exception.getMsg());
    }
    /**
     * 控制器参数序列化出错
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public CommonResponse defaultHttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.error("控制器参数序列化出错,{}", ex);
//        String errors = "";
//        InvalidFormatException e = (InvalidFormatException) ex.getCause();
//        List<JsonMappingException.Reference> path = e.getPath();
//        for(JsonMappingException.Reference reference : path){
//            errors += "参数名："+reference.getFieldName()+" 输入不合法，需要的是 "+e.getTargetType().getName() + " 类型，"+"提交的值是："+e.getValue().toString();
//            log.info("参数名：{}",reference.getFieldName());
//        }
//        log.info("提交的参数值：{}",e.getValue().toString());
//        log.info("需要的参数类型：{}"+e.getTargetType().getName());

        return CommonResponse.failed(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMsg());
    }

    /**
     * BindException
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResponse defaultHttpBindExceptionHandler(BindException exception) {
        log.error("BindException e,{}", exception);
        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
        String validationMsg = objectError.getDefaultMessage();
        if (!NumberUtil.isInteger(objectError.getDefaultMessage())) {
            validationMsg = ((FieldError) objectError).getField() + validationMsg;
        }
        return CommonResponse.failed(ErrorCodeEnum.PARAM_ERROR.getCode(), validationMsg);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public CommonResponse missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException e,{}", e);
        String parameterName = e.getParameterName();
        return CommonResponse.failed(ErrorCodeEnum.PARAM_ERROR.getCode(), parameterName + "不能为空");
    }

    /**
     * 参数校验
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse defaultMethodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException e) {
        log.warn("参数校验异常:{}", e);
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String validationMsg = objectError.getDefaultMessage();
        return CommonResponse.failed(ErrorCodeEnum.PARAM_ERROR.getCode(), validationMsg);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CommonResponse handleMethodArgumentTypeMismatchExceptions(Exception ex) {
        log.warn(ex.getMessage(), ex);
        //DingAlertService.dingDingAlert("全局异常Excepiton", ex);
        return CommonResponse.failed(ErrorCodeEnum.PARAM_TYPE_ERROR.getCode(), ErrorCodeEnum.PARAM_TYPE_ERROR.getMsg());
    }



    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseBody
    public CommonResponse<String> defaultIllegalStateExceptionHandler(HttpServletRequest req,
                                                                      IllegalStateException e) {
        log.error("IllegalStateException,{}", e);
        BizException bizException = new BizException(ErrorCodeEnum.SYSTEM_ERROR);
        return CommonResponse.failed(bizException.getCode(), bizException.getMsg());
    }

//    @ExceptionHandler(value = BadSqlGrammarException.class)
//    @ResponseBody
//    public CommonResponse<String> sqLSyntaxErrorExceptionHandler(HttpServletRequest req,
//                                                                 BadSqlGrammarException e) {
//        // 报警
//        log.error("数据库执行错误BadSqlGrammarException,{}", e);
//        BizException bizException = new BizException(ErrorCodeEnum.SYSTEM_ERROR);
//        return CommonResponse.failed(bizException.getCode(), bizException.getMsg());
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public CommonResponse<String> defaultConstraintViolationExceptionHandler(HttpServletRequest req,
                                                                             ConstraintViolationException e) {
        log.error("ConstraintViolationException,{}", e);
        BizException bizException = new BizException(ErrorCodeEnum.SYSTEM_ERROR);
        return CommonResponse.failed(bizException.getCode(), bizException.getMsg());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("请求方式错误 {}", e);
        return CommonResponse.failed(ErrorCodeEnum.NOT_SUPPORTED_REQ_METHOD.getCode(), ErrorCodeEnum.NOT_SUPPORTED_REQ_METHOD.getMsg());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse handleException(Exception exception, HttpServletRequest request) {
        log.error("全局异常{}", exception);
        return CommonResponse.failed(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMsg());
    }

}
