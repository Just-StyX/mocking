package jsl.com.mocking.second;

import java.util.List;

public record AstronautResponse(int number, String message, List<Assignment> people) {
}
