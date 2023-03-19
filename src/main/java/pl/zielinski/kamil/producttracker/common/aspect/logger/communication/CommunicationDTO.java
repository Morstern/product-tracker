package pl.zielinski.kamil.producttracker.common.aspect.logger.communication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

@Builder
@AllArgsConstructor
@Getter
public class CommunicationDTO {

    private RequestMethod requestMethod;
    private String controllerName;
    private String methodName;
    private Object body;
    private CommunicationDirection communicationDirection;

    public enum CommunicationDirection {
        REQUEST("==>"),
        RESPONSE("<==");

        private final String directionSign;

        CommunicationDirection(String directionSign) {
            this.directionSign = directionSign;
        }

        @Override
        public String toString() {
            return directionSign;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s#%s %nBody: %s", requestMethod, communicationDirection, controllerName, methodName, body);
    }
}
