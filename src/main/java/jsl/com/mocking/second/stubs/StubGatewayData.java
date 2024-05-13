package jsl.com.mocking.second.stubs;

import jsl.com.mocking.second.Assignment;
import jsl.com.mocking.second.AstronautResponse;
import jsl.com.mocking.second.Gateway;

import java.util.List;

public class StubGatewayData implements Gateway<AstronautResponse> {
    @Override
    public AstronautResponse getResponse() {
        return new AstronautResponse(
                7, "job done",
                List.of(
                        new Assignment("Albert Einstein", "ISS"),
                        new Assignment("Robert Hill", "USS Voyager"),
                        new Assignment("Chun Kai", "Beijin Aero"),
                        new Assignment("Thomas Muller", "German K8"),
                        new Assignment("Frank Thomason", "ISS"),
                        new Assignment("Captain 774", "ISS"),
                        new Assignment("Naomi Campbell", "UK Missions"),
                        new Assignment("Golf Mediator", "USS Voyager"),
                        new Assignment("Kofi Nkum", "GH 57"),
                        new Assignment("Krilov Sergie", "USSR")
                )
        );
    }
}
