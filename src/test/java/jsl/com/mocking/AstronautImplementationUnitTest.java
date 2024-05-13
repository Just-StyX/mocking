package jsl.com.mocking;

import jsl.com.mocking.second.AstronautImplementation;
import jsl.com.mocking.second.stubs.StubGatewayData;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AstronautImplementationUnitTest {
    @Test
    public void unitTestingAstronaut() {
        var service = new AstronautImplementation(new StubGatewayData());

        Map<String, Long> data = service.getData();

        data.forEach((craft, number) -> {
            System.out.printf("%d space men aboard %s%n", number, craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }
}
