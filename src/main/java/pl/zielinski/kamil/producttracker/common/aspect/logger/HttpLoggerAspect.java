package pl.zielinski.kamil.producttracker.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pl.zielinski.kamil.producttracker.common.log.AbstractLogger;

@Aspect
@Component
public class HttpLoggerAspect extends AbstractLogger {
    @Around("@annotation(pl.zielinski.kamil.producttracker.common.annotation.HttpLogger)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logInfo("Calling from HttpLoggerAspect");
        return joinPoint.proceed();
    }
}
