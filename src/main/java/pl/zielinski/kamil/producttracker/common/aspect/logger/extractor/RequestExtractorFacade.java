package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestExtractorFacade {
    private final RequestMethodExtractor requestMethodExtractor;
    private final RequestMethodNameExtractor requestMethodNameExtractor;
    private final RequestControllerNameExtractor requestControllerNameExtractor;

    @Autowired
    public RequestExtractorFacade(RequestMethodExtractor requestMethodExtractor, RequestMethodNameExtractor requestMethodNameExtractor,
                                  RequestControllerNameExtractor requestControllerNameExtractor) {
        this.requestMethodExtractor = requestMethodExtractor;
        this.requestMethodNameExtractor = requestMethodNameExtractor;
        this.requestControllerNameExtractor = requestControllerNameExtractor;
    }

    /**
     * Method which is an entry point for HttpLoggerAspect in order to fetch basic data about Request: HTTP method, name of controller and method
     *
     * @param methodSignature interface which allows to get access to needed information
     * @return {@link Request} object which holds basic data
     */
    public Request extractRequest(MethodSignature methodSignature) {
        return Request.builder()
                .requestMethod(requestMethodExtractor.extract(methodSignature))
                .requestControllerName(requestControllerNameExtractor.extract(methodSignature))
                .requestMethodName(requestMethodNameExtractor.extract(methodSignature))
                .build();
    }
}
