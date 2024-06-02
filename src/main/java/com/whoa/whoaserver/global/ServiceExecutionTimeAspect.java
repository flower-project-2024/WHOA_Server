package com.whoa.whoaserver.global;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ServiceExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExecutionTimeAspect.class);

    @Around("execution(* com.whoa.whoaserver.keyword.service.FlowerKeywordService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }
}
