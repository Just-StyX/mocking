package jsl.com.mocking.second;

import java.util.Map;
import java.util.stream.Collectors;

public class AstronautImplementation {
    private final Gateway<AstronautResponse> astronautResponseGateway;

    public AstronautImplementation(Gateway<AstronautResponse> astronautResponseGateway) {
        this.astronautResponseGateway = astronautResponseGateway;
    }

    public Map<String, Long> getData() {
        AstronautResponse astronautResponse = astronautResponseGateway.getResponse();
        return groups(astronautResponse);
    }

    private Map<String, Long> groups(AstronautResponse data) {
        return data.people().stream().collect(
                Collectors.groupingBy(Assignment::craft, Collectors.counting())
        );
    }
}
