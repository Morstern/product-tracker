package pl.zielinski.kamil.producttracker.common.aspect.logger.communication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class HttpLog {

    private String path;
    private String requestMethod;
    private String requestUrl;
    private String methodName;
    private String controllerName;
    private CommunicationDirection communicationDirection;
    private String requesterName;
    @Builder.Default
    private Object body = "";
    @Builder.Default
    private String statusCode = "";

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
        return String.format("[%s] %s %s %s %s#%s %s %s",
                requestMethod, requestUrl, requesterName, communicationDirection, controllerName, methodName, statusCode, body);
    }
}
