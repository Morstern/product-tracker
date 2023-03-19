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
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.RequestExtractorFacade;
import pl.zielinski.kamil.producttracker.common.log.Log;

@Aspect
@Component
@EnableAspectJAutoProxy
public class HttpLoggerAspect {

    private final Log log;
    private final RequestExtractorFacade requestExtractorFacade;
    private final CommunicationFactory communicationFactory;

    @Autowired
    public HttpLoggerAspect(Log log, RequestExtractorFacade requestExtractorFacade, CommunicationFactory communicationFactory) {
        this.log = log;
        this.requestExtractorFacade = requestExtractorFacade;
        this.communicationFactory = communicationFactory;
    }

    @Pointcut(value = "@within(pl.zielinski.kamil.producttracker.common.annotation.architecture.KZRestController)")
    private void withinKZRestController() {
    }

    @Around(value = "withinKZRestController()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Request request = requestExtractorFacade.extractRequest(methodSignature);

        log.info(communicationFactory.createRequestDTO(request).toString());

        // Even when throwing exceptions we will map it to ResponseEntity with ExceptionMapperAspect
        ResponseEntity responseEntity = (ResponseEntity) joinPoint.proceed();

        log.info(communicationFactory.createResponseDTO(request, responseEntity.getBody()).toString());

        return responseEntity;
    }
}
