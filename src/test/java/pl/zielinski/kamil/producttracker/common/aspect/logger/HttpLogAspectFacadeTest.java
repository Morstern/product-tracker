package pl.zielinski.kamil.producttracker.common.aspect.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import pl.zielinski.kamil.producttracker.base.BaseUnitTest;
import pl.zielinski.kamil.producttracker.common.aspect.logger.communication.HttpLog;
import pl.zielinski.kamil.producttracker.common.aspect.logger.communication.HttpLogFactory;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.Request;
import pl.zielinski.kamil.producttracker.common.aspect.logger.extractor.RequestExtractorFacade;
import pl.zielinski.kamil.producttracker.common.log.LogFacade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HttpLogAspectFacadeTest extends BaseUnitTest {
    @InjectMocks
    HttpLoggerAspect httpLoggerAspect;
    @Mock
    RequestExtractorFacade requestExtractorFacade;
    @Mock
    HttpLogFactory httpLogFactory;
    @Mock
    LogFacade logFacade;

    @Test
    public void shouldLogRequestAndResponse() throws Throwable {
        // given
        ProceedingJoinPoint mockProceedingJoinPoint = Mockito.mock(ProceedingJoinPoint.class);
        MethodSignature mockMethodSignature = Mockito.mock(MethodSignature.class);
        Request mockRequest = Request.builder().build();
        ResponseEntity mockResponse = ResponseEntity.ok().build();
        HttpLog mockHttpLog = HttpLog.builder().build();

        // when
        when(mockProceedingJoinPoint.getSignature())
                .thenReturn(mockMethodSignature);
        when(requestExtractorFacade.getRequestData(mockMethodSignature))
                .thenReturn(mockRequest);
        when(httpLogFactory.createRequestDTO(mockRequest))
                .thenReturn(mockHttpLog);
        when(httpLogFactory.createResponseDTO(mockRequest, mockResponse))
                .thenReturn(mockHttpLog);
        when(mockProceedingJoinPoint.proceed())
                .thenReturn(mockResponse);

        // then
        Object result = httpLoggerAspect.logRequestAndResponse(mockProceedingJoinPoint);

        assertThat(result).isEqualTo(mockResponse);

        verify(requestExtractorFacade).getRequestData(mockMethodSignature);
        verify(httpLogFactory).createRequestDTO(mockRequest);
        verify(httpLogFactory).createResponseDTO(mockRequest, mockResponse);
        verify(logFacade, times(2)).info(any());
    }
}