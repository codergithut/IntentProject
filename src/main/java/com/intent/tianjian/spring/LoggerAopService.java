package com.intent.tianjian.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class LoggerAopService {

    Logger logger = LoggerFactory.getLogger(LoggerAopService.class);



    @Pointcut("@annotation(com.intent.tianjian.spring.LogPrint)")
    private void cutMethod() {

    }

    @Around("cutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Long start = System.currentTimeMillis();

        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 执行源方法
        Object o = joinPoint.proceed();
        // 模拟进行验证

        Long end = System.currentTimeMillis();

        logger.info(methodName + ":" + (end - start));

        return o;



    }
}
