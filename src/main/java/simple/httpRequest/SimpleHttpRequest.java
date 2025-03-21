package simple.httpRequest;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Builder
@Getter
public class SimpleHttpRequest {
    private final String requestLine;
    private final HashMap<String, String> header = new HashMap<>();
    private final String jsonBody;
}
