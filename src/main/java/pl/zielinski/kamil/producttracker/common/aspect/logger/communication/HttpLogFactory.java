package pl.zielinski.kamil.producttracker.common.aspect.logger.communication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.Request;

@Component
public class HttpLogFactory {
    private final static String BODY_FORMAT = "%n Body: %s";
    private final static String STATUS_CODE_FORMAT = "StatusCode: [%d %s]";

    public HttpLog createRequestDTO(Request request) {
        return HttpLog.builder()
                .communicationDirection(HttpLog.CommunicationDirection.REQUEST)
                .requestMethod(request.getRequestMethod())
                .requestUrl(request.getRequestUrl())
                .methodName(request.getRequestMethodName())
                .controllerName(request.getRequestControllerName())
                .requesterName(request.getRequesterName())
                .build();
    }

    public HttpLog createRequestDTO(Request request, Object body) {
        return HttpLog.builder()
                .communicationDirection(HttpLog.CommunicationDirection.REQUEST)
                .requestMethod(request.getRequestMethod())
                .requestUrl(request.getRequestUrl())
                .methodName(request.getRequestMethodName())
                .controllerName(request.getRequestControllerName())
                .requesterName(request.getRequesterName())
                .body(String.format(BODY_FORMAT, body))
                .build();
    }

    public HttpLog createResponseDTO(Request request, ResponseEntity<?> responseEntity) {
        return HttpLog.builder()
                .communicationDirection(HttpLog.CommunicationDirection.RESPONSE)
                .requestMethod(request.getRequestMethod())
                .requestUrl(request.getRequestUrl())
                .methodName(request.getRequestMethodName())
                .controllerName(request.getRequestControllerName())
                .requesterName(request.getRequesterName())
                .body(String.format(BODY_FORMAT, responseEntity.getBody()))
                .statusCode(String.format(STATUS_CODE_FORMAT, responseEntity.getStatusCode().value(), responseEntity.getStatusCode().getReasonPhrase()))
                .build();
    }
}
