package com.daniil.sockinventorysystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before(value = "execution(* com.daniil.sockinventorysystem.services.*.*(..)) " +
            "|| execution(* com.daniil.sockinventorysystem.controllers.*.*(..))")
    public void logBeforeStart(JoinPoint joinPoint) {
        logger.trace(String.format(
                "Method %s will execute with args: %s",
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
    }

    @AfterReturning(value = "execution(* com.daniil.sockinventorysystem.services.*.*(..)) " +
            "|| execution(* com.daniil.sockinventorysystem.controllers.*.*(..))", returning = "result")
    public void logAfterReturn(JoinPoint joinPoint, Object result) {
        logger.trace(String.format(
                "Method %s executed and returned: %s",
                joinPoint.getSignature().getName(), result));
    }

    @AfterThrowing(value = "execution(* com.daniil.sockinventorysystem.services.*.*(..)) " +
            "|| execution(* com.daniil.sockinventorysystem.controllers.*.*(..))",
            throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error(String.format("Exception occurred in method %s with message: %s",
                joinPoint.getSignature().getName(), ex.getMessage()));
    }
}
