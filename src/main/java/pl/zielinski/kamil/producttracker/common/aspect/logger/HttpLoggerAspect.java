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
import pl.zielinski.kamil.producttracker.common.aspect.logger.communication.HttpLogFactory;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.Request;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.RequestExtractorFacade;
import pl.zielinski.kamil.producttracker.common.log.LogFacade;

@Aspect
@Component
@EnableAspectJAutoProxy
public class HttpLoggerAspect {

    private final LogFacade logFacade;
    private final RequestExtractorFacade requestExtractorFacade;
    private final HttpLogFactory httpLogFactory;

    @Autowired
    public HttpLoggerAspect(LogFacade logFacade, RequestExtractorFacade requestExtractorFacade,
                            HttpLogFactory httpLogFactory) {
        this.logFacade = logFacade;
        this.requestExtractorFacade = requestExtractorFacade;
        this.httpLogFactory = httpLogFactory;
    }

    @Pointcut(value = "@within(pl.zielinski.kamil.producttracker.common.annotation.architecture.KZRestController)")
    private void withinKZRestController() {
    }

    @Around(value = "withinKZRestController()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Request request = requestExtractorFacade.getRequestData(methodSignature);

        logFacade.info(httpLogFactory.createRequestDTO(request).toString());

        // Even when throwing exceptions we will map it to ResponseEntity with ExceptionMapperAspect
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) joinPoint.proceed();

        logFacade.info(httpLogFactory.createResponseDTO(request, responseEntity).toString());

        return responseEntity;
    }
}
