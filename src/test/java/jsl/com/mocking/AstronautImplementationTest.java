package jsl.com.mocking;

import jsl.com.mocking.second.AstronautImplementation;
import jsl.com.mocking.second.GatewayHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AstronautImplementationTest {

    @Test
    void testDataWithWebClient(TestReporter reporter) {
        var service = new AstronautImplementation(new GatewayHttpClient());
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
