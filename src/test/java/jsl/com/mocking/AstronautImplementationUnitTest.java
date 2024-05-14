package jsl.com.mocking;

import jsl.com.mocking.second.AstronautImplementation;
import jsl.com.mocking.second.AstronautResponse;
import jsl.com.mocking.second.Gateway;
import jsl.com.mocking.second.stubs.StubGatewayData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AstronautImplementationUnitTest {
    @Mock
    private Gateway<AstronautResponse> astronautResponseGateway;
    @InjectMocks
    private AstronautImplementation astronautImplementation;

    @Test
    public void unitTestingAstronaut() {
        when(astronautResponseGateway.getResponse()).thenReturn(StubGatewayData.getResponse());

        Map<String, Long> data = astronautImplementation.getData();

        data.forEach((craft, number) -> {
            System.out.printf("%d space men aboard %s%n", number, craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
            verify(astronautResponseGateway).getResponse();
        });
    }

    @Test
    public void failedNetworkException() {
        when(astronautResponseGateway.getResponse()).thenThrow(new RuntimeException(new IOException("Network failure")));
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> astronautImplementation.getData())
                .withCauseInstanceOf(IOException.class)
                .withMessageContaining("Network failure");
    }
}
