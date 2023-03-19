package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

@Getter
@AllArgsConstructor
@Builder
public class Request {
    RequestMethod requestMethod;
    String requestControllerName;
}
