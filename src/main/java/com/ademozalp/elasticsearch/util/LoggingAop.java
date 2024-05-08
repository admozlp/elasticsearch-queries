package com.ademozalp.elasticsearch.util;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAop {

    private final Logger log = LoggerFactory.getLogger(LoggingAop.class);

    @Pointcut("execution(* com.ademozalp.elasticsearch.service.*.*(..))")
    private void productService(){}

    @Before("productService()")
    public void before(JoinPoint joinPoint){
        log.info(joinPoint.getSignature().getName().concat(" Methodu cagrildi"));
    }

    @AfterReturning(value = "productService()", returning = "result")
    public void after(JoinPoint joinPoint, Object result){
        log.info(joinPoint.getSignature().getName().concat(" Methodu : ")
                .concat(result.toString()));
    }

    @AfterThrowing(value = "productService()", throwing = "exception")
     public void afterThrowing(Exception exception){
        StackTraceElement[] stackTrace = exception.getStackTrace();

        String info = stackTrace[0].getFileName() +
                ":" +
                stackTrace[0].getMethodName() +
                "()>>" +
                stackTrace[0].getLineNumber();

        log.error(info.concat(":").concat(exception.getMessage()));
     }
}
