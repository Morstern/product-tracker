package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Request {
    private String requestMethod;
    private String requestUrl;
    private String requesterName;
    private String requestMethodName;
    private String requestControllerName;
}
