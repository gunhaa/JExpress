package simple.requestHandler;

import java.util.HashMap;

public class SimpleHttpRequest {
    private String method;
    private String url;
    private HashMap<String, String> header = new HashMap<>();
    private String jsonBody;
}
