package com.codingshuttle.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.codingshuttle.aopApp.service.impl.ShipmentServiceImpl.*(..))")
    public void beforeShipmentServiceImplMethods(JoinPoint joinPoint){
        log.info("Before calling from LoggingAspect {}",joinPoint.getSignature());
    }

    @Before("within(com.codingshuttle.aopApp..*)")
    public void beforeCallingServiceClasses(){
        log.info("Calling from LoggingAspect ");
    }

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransactionalCalls(){
        log.info("Before Transactional call ");
    }

    @Before("myLoggingPointCut()")
    public void beforeMyLoggingCalls(){
        log.info("Before MyLogging call ");
    }

    @After("myLoggingPointCut()")
    public void afterMyLoggingCalls(){
        log.info("After MyLogging call ");
    }

    @Pointcut("@annotation(com.codingshuttle.aopApp.aspects.annotations.MyLogging) " +
            "&& within(com.codingshuttle.aopApp..*)")
    public void myLoggingPointCut(){}
}
