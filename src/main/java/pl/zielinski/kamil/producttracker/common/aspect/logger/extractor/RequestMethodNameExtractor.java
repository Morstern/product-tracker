package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
public class RequestMethodNameExtractor implements RequestExtractor<String, MethodSignature> {

    @Override
    public String extract(MethodSignature methodSignature) {
        return methodSignature.getMethod().getName();
    }
}
