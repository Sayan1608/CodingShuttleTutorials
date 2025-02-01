package com.codingshuttle.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class LoggingAspectV2 {
    @Pointcut("execution(* com.codingshuttle.aopApp.service.impl.*.*(..))")
    public void allServiceMethodPointCut(){}

    @Before("allServiceMethodPointCut()")
    public void beforeServiceMethodCalls(JoinPoint joinPoint){
        log.info("Before Service method calls in LoggingAspectV2 {}",joinPoint.getSignature());
    }

    @After("allServiceMethodPointCut()")
    public void afterServiceMethodCalls(JoinPoint joinPoint){
        log.info("After Service method calls in LoggingAspectV2 {}",joinPoint.getSignature());
    }

    @AfterReturning(value = "allServiceMethodPointCut()", returning = "returnedObj")
    public void afterReturningServiceMethodCalls(JoinPoint joinPoint, Object returnedObj){
        log.info("After returning Service method calls in LoggingAspectV2 {}",joinPoint.getSignature());
        log.info("Returned value from {}, {} ",joinPoint.getSignature(),returnedObj);
    }

    @AfterThrowing(value = "allServiceMethodPointCut()",throwing = "thrownObj")
    public void afterThrowingServiceMethodCalls(JoinPoint joinPoint, Object thrownObj){
        log.info("After throwing Service method calls in LoggingAspectV2 {}",joinPoint.getSignature());
        log.error("Error in {} , {}",joinPoint.getSignature(),thrownObj);
    }

    @Around("allServiceMethodPointCut()")
    public Object getExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnedObj = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Time taken for executing {}, {} ",proceedingJoinPoint.getSignature(),(endTime-startTime));
        return returnedObj;
    }
}
