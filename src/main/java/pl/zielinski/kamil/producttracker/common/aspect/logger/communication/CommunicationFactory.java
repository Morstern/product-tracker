package pl.zielinski.kamil.producttracker.common.aspect.logger.communication;

import org.springframework.stereotype.Component;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.Request;

@Component
public class CommunicationFactory {
    public CommunicationDTO createRequestDTO(Request request) {
        return CommunicationDTO.builder()
                .communicationDirection(CommunicationDTO.CommunicationDirection.REQUEST)
                .requestMethod(request.getRequestMethod())
                .controllerName(request.getRequestControllerName())
                .methodName(request.getRequestMethodName())
                .build();
    }

    public CommunicationDTO createRequestDTO(Request request, Object body) {
        return CommunicationDTO.builder()
                .communicationDirection(CommunicationDTO.CommunicationDirection.REQUEST)
                .requestMethod(request.getRequestMethod())
                .controllerName(request.getRequestControllerName())
                .methodName(request.getRequestMethodName())
                .body(body)
                .build();
    }

    public CommunicationDTO createResponseDTO(Request request, Object body) {
        return CommunicationDTO.builder()
                .communicationDirection(CommunicationDTO.CommunicationDirection.RESPONSE)
                .requestMethod(request.getRequestMethod())
                .controllerName(request.getRequestControllerName())
                .methodName(request.getRequestMethodName())
                .body(body)
                .build();
    }
}
