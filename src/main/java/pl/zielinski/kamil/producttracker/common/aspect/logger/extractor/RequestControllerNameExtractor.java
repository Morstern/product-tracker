package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pl.zielinski.kamil.producttracker.common.exception.UnexpectedException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RequestControllerNameExtractor implements RequestExtractor<String, MethodSignature> {

    private final String REQUEST_CONTROLLER_NAME_NOT_FOUND_EXCEPTION = "Request Controller name not found within: %s";

    private final String REGEX = "(pl.zielinski.*)([A-Z].*Controller)$";
    private final Pattern REGEX_PATTERN = Pattern.compile(REGEX);

    @Override
    public String extract(MethodSignature methodSignature) {
        String nameWithPackage = methodSignature.getDeclaringType().getName();

        Matcher m = REGEX_PATTERN.matcher(nameWithPackage);

        if (m.find()) {
            return m.group(2);
        }

        throw new UnexpectedException(String.format(REQUEST_CONTROLLER_NAME_NOT_FOUND_EXCEPTION, nameWithPackage));
    }
}
