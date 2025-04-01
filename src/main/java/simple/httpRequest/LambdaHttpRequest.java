package simple.httpRequest;

import simple.constant.HttpStatus;

import java.util.HashMap;
import java.util.Optional;

public class LambdaHttpRequest {

    private final HttpRequest httpRequest;

    public LambdaHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getQueryString(String target){
        HashMap<String, String> qs = httpRequest.getQueryString();
        String value = qs.get(target);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "this url must need field"));
        }
        return value;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
