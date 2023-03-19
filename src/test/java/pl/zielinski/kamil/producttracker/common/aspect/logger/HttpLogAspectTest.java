package pl.zielinski.kamil.producttracker.common.aspect.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import pl.zielinski.kamil.producttracker.base.BaseUnitTest;
import pl.zielinski.kamil.producttracker.common.aspect.logger.communication.CommunicationDTO;
import pl.zielinski.kamil.producttracker.common.aspect.logger.communication.CommunicationFactory;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.Request;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.RequestExtractorFacade;
import pl.zielinski.kamil.producttracker.common.log.Log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HttpLogAspectTest extends BaseUnitTest {
    @InjectMocks
    HttpLoggerAspect httpLoggerAspect;
    @Mock
    RequestExtractorFacade requestExtractorFacade;
    @Mock
    CommunicationFactory communicationFactory;
    @Mock
    Log log;

    @Test
    public void shouldLogRequestAndResponse() throws Throwable {
        // given
        ProceedingJoinPoint mockProceedingJoinPoint = Mockito.mock(ProceedingJoinPoint.class);
        MethodSignature mockMethodSignature = Mockito.mock(MethodSignature.class);
        Request mockRequest = Request.builder().build();
        ResponseEntity mockResponse = ResponseEntity.ok().build();
        CommunicationDTO mockCommunicationDTO = CommunicationDTO.builder().build();

        // when
        when(mockProceedingJoinPoint.getSignature())
                .thenReturn(mockMethodSignature);
        when(requestExtractorFacade.extractRequest(mockMethodSignature))
                .thenReturn(mockRequest);
        when(communicationFactory.createRequestDTO(mockRequest))
                .thenReturn(mockCommunicationDTO);
        when(communicationFactory.createResponseDTO(mockRequest, mockResponse.getBody()))
                .thenReturn(mockCommunicationDTO);
        when(mockProceedingJoinPoint.proceed())
                .thenReturn(mockResponse);

        // then
        Object result = httpLoggerAspect.logRequestAndResponse(mockProceedingJoinPoint);

        assertThat(result).isEqualTo(mockResponse);

        verify(requestExtractorFacade).extractRequest(mockMethodSignature);
        verify(communicationFactory).createRequestDTO(mockRequest);
        verify(communicationFactory).createResponseDTO(mockRequest, mockResponse.getBody());
        verify(log, times(2)).info(any());
    }
}