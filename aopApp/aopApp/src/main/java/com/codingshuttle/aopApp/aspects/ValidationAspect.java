package com.codingshuttle.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidationAspect {
    @Pointcut("execution(* com.codingshuttle.aopApp.service.impl.*.*(..))")
    public void allServiceMethodPointCut(){}

    @Around("allServiceMethodPointCut()")
    public Object validateServiceCalls(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        Long orderId = (Long)args[0];
        if(orderId > 0) return proceedingJoinPoint.proceed();
        else return "Cannot proceed with negative order Id";
    }
}
