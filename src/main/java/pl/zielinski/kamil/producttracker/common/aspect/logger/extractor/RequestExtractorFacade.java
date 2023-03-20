package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import pl.zielinski.kamil.producttracker.common.annotation.architecture.KZFacade;

import javax.servlet.http.HttpServletRequest;

@KZFacade
public class RequestExtractorFacade {
    private final HttpServletRequest httpServletRequest;
    private final RequestMethodNameExtractor requestMethodNameExtractor;
    private final RequestControllerNameExtractor requestControllerNameExtractor;

    @Autowired
    public RequestExtractorFacade(HttpServletRequest httpServletRequest, RequestMethodNameExtractor requestMethodNameExtractor, RequestControllerNameExtractor requestControllerNameExtractor) {
        this.httpServletRequest = httpServletRequest;
        this.requestMethodNameExtractor = requestMethodNameExtractor;
        this.requestControllerNameExtractor = requestControllerNameExtractor;
    }

    /**
     * Method which is an entry point for HttpLoggerAspect in order to fetch basic data about Request: HTTP method, name of controller and method
     *
     * @return {@link Request} object which holds basic data
     */
    public Request getRequestData(MethodSignature methodSignature) {
        return Request.builder()
                .requestMethod(httpServletRequest.getMethod())
                .requestUrl(httpServletRequest.getRequestURL().toString())
                .requesterName(httpServletRequest.getUserPrincipal().getName())
                .requestMethodName(requestMethodNameExtractor.extract(methodSignature))
                .requestControllerName(requestControllerNameExtractor.extract(methodSignature))
                .build();
    }
}
