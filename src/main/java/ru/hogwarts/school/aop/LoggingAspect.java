package ru.hogwarts.school.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@ru.hogwarts.school.annotation.LogNameOfRunningMethod *) || @annotation(ru.hogwarts.school.annotation.LogNameOfRunningMethod)")
    public void methodNameWritingProcessing() {
    }

    @Before("methodNameWritingProcessing()")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Метод " + methodName + " класса " + className + " был вызван.");
    }
}
