package jsl.com.mocking.second;

import com.google.gson.Gson;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GatewayHttpClient implements Gateway<AstronautResponse>{
    private static final String ASTRONAUT_URL = "http://api.open-notify.org/";
    private final String url;

    public GatewayHttpClient() {
        this(ASTRONAUT_URL);
    }
    public GatewayHttpClient(String url) {
        this.url = url;
    }

    @Override
    public AstronautResponse getResponse() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "astros.json"))
                    .timeout(Duration.ofSeconds(2))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return new Gson().fromJson(response.body(), AstronautResponse.class);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
