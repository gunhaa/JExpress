package simple.requestHandler;

import java.util.HashMap;

public class SimpleHttpRequest {
    private String method;
    private String url;
    private HashMap<String, String> header = new HashMap<>();
    private String jsonBody;
    private boolean isRequestBody = false;
    private boolean isFirstLine = true;

    public SimpleHttpRequest() {
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addHeader(String line) {
        this.header.put("header", line);
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public boolean isRequestBody () {
        return isRequestBody;
    }

    public void setIsRequestBody (boolean body) {
        this.isRequestBody = body;
    }

    public boolean isFirstLine() {
        return isFirstLine;
    }

    public void toggleIsFirstLine() {
        this.isFirstLine = isFirstLine;
    }
}
