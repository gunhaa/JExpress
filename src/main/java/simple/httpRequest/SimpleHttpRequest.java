package simple.httpRequest;

import lombok.Builder;
import lombok.Getter;
import simple.httpMethod.HttpMethod;

import java.util.HashMap;

@Builder
@Getter
public class SimpleHttpRequest {
    private HttpMethod method;
    private String url;
    private String protocol;
    private final HashMap<String, String> queryString = new HashMap<>();
    private final HashMap<String, String> header = new HashMap<>();
    private StringBuilder body;
}
