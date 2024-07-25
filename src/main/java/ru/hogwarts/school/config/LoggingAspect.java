package ru.hogwarts.school.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("@annotation(ru.hogwarts.school.config.annotation.LogNameOfRunningMethod)")
    public void methodNameWritingProcessing() {

    }

    @Before("within(@ru.hogwarts.school.config.annotation.LogNameOfRunningMethod *)")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Метод " + methodName + " класса " + className + " был вызван.");
    }
}
