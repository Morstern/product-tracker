package pl.zielinski.kamil.producttracker.common.aspect.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.zielinski.kamil.producttracker.common.aspect.logger.communication.CommunicationFactory;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.Request;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.RequestControllerNameExtractor;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.RequestMethodExtractor;
import pl.zielinski.kamil.producttracker.common.log.AbstractLogger;

@Aspect
@Component
@EnableAspectJAutoProxy
public class HttpLoggerAspect extends AbstractLogger {

    private final RequestMethodExtractor requestMethodExtractor;
    private final RequestControllerNameExtractor requestControllerNameExtractor;
    private final CommunicationFactory communicationFactory;

    @Autowired
    public HttpLoggerAspect(RequestMethodExtractor requestMethodExtractor, RequestControllerNameExtractor requestControllerNameExtractor,
                            CommunicationFactory communicationFactory) {
        this.requestMethodExtractor = requestMethodExtractor;
        this.requestControllerNameExtractor = requestControllerNameExtractor;
        this.communicationFactory = communicationFactory;
    }

    @Pointcut(value = "@within(pl.zielinski.kamil.producttracker.common.annotation.architecture.KZRestController)")
    private void withinKZRestController() {
    }

    @Around(value = "withinKZRestController()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Request request = Request.builder()
                .requestMethod(requestMethodExtractor.extract(signature))
                .requestControllerName(requestControllerNameExtractor.extract(signature))
                .build();

        logInfo(communicationFactory.createRequestDTO(request).toString());

        // Even when throwing exceptions we will map it to ResponseEntity
        ResponseEntity responseEntity = (ResponseEntity) joinPoint.proceed();

        logInfo(communicationFactory.createResponseDTO(request, responseEntity.getBody()).toString());

        return responseEntity;
    }
}
