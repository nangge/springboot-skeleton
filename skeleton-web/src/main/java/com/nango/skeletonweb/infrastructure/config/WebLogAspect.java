package com.nango.skeletonweb.infrastructure.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

  /**
   * 以 controller 包下定义的所有请求为切入点
   */
  @Pointcut("execution(public * com.nango.skeletonweb.interfaces..*.*(..))")
  public void webLog() {
  }


  /**
   * 在切点之前织入
   */
  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    // 开始打印请求日志
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    // 打印请求相关参数
    log.info(
        "========================================== Start ==========================================");
    // 打印请求 url
    log.info("URL            : {}", request.getRequestURL().toString());

    // 打印请求 contentType
    log.info("contentType            : {}", request.getContentType());

    // 打印 Http method
    log.info("HTTP Method    : {}", request.getMethod());
    // 打印调用 controller 的全路径以及执行方法
    log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName());
    // 打印请求的 IP
    log.info("IP             : {}", request.getRemoteAddr());
    // 打印请求入参
    try {
      log.info("Request Args   : {}", joinPoint.getArgs());
    } catch (Throwable e) {
      log.info("Request Args   : {}", joinPoint.getArgs());
    }

  }

  /**
   * 在切点之后织入
   */
  @After("webLog()")
  public void doAfter() throws Throwable {
    log.info(
        "=========================================== End ===========================================");
    // 每个请求之间空一行
    log.info("");
  }

  /**
   * 环绕
   */
  @Around("webLog()")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result;
    try {
      result = proceedingJoinPoint.proceed();
      // 打印出参
      log.info("Response Args  : {}", JSONUtil.toJsonStr(result));
    } finally {

      // 执行耗时
      log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
    }
    return result;
  }

  @AfterThrowing(value = "webLog()", throwing = "e")
  public void afterThrowing(JoinPoint joinPoint, Exception e) {
    log.info("Response Args e : {}", e);
  }
}
