package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.zielinski.kamil.producttracker.common.exception.UnexpectedException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class RequestMethodExtractor implements RequestExtractor<RequestMethod, MethodSignature> {
    private final String METHOD_SHOULD_HAVE_EXACTLY_ONE_REQUEST_MAPPING_ERROR_MESSAGE = "Method should have exactly one RequestMapping";
    private final String REQUEST_MAPPING_ANNOTATION_SHOULD_HAVE_EXACTLY_ONE_METHOD = "RequestMapping annotation should have exactly one method";

    private final Predicate<Annotation> IS_REQUEST_MAPPING_PRESENT = annotation -> annotation.annotationType().isAnnotationPresent(RequestMapping.class);

    public RequestMethod extract(MethodSignature methodSignature) {
        Annotation[] annotations = methodSignature.getMethod().getDeclaredAnnotations();

        List<RequestMapping> annotationsWithRequestMapping = Arrays.stream(annotations)
                .filter(IS_REQUEST_MAPPING_PRESENT)
                .flatMap(annotation -> Arrays.stream(annotation.annotationType().getAnnotationsByType(RequestMapping.class)))
                .collect(Collectors.toUnmodifiableList());

        if (annotationsWithRequestMapping.size() > 1) {
            throw new UnexpectedException(METHOD_SHOULD_HAVE_EXACTLY_ONE_REQUEST_MAPPING_ERROR_MESSAGE);
        }

        RequestMapping annotation = annotationsWithRequestMapping.stream()
                .findFirst()
                .orElseThrow(() -> new UnexpectedException(METHOD_SHOULD_HAVE_EXACTLY_ONE_REQUEST_MAPPING_ERROR_MESSAGE));

        if (annotation.method().length > 1) {
            // TODO: after implementing arch test -> we can remove this check
            //  Reason: in this project we don't allow to have multiple methods on one endpoint - also from architectural perspective it does not make sense
            throw new UnexpectedException(REQUEST_MAPPING_ANNOTATION_SHOULD_HAVE_EXACTLY_ONE_METHOD);
        }

        return Arrays.stream(annotation.method())
                .findFirst()
                .orElseThrow(() -> new UnexpectedException(REQUEST_MAPPING_ANNOTATION_SHOULD_HAVE_EXACTLY_ONE_METHOD));
    }
}
