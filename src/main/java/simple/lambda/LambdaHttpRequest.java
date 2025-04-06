package simple.lambda;

import simple.constant.CustomHttpMethod;
import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class LambdaHttpRequest {

    private final HttpRequest httpRequest;

    public LambdaHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getParam(String binding){
        HashMap<String, String> params = httpRequest.getParams();
        return params.get(binding);
    }

    public String getQueryString(String binding){
        HashMap<String, String> qs = httpRequest.getQueryString();
        String value = qs.get(binding);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid queryString request"));
        }
        return value;
    }

    public String getBody(String binding){
        Map<String, String> body = httpRequest.getBodyMap();
        String value = body.get(binding);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid body request"));
        }
        return value;
    }

    public Map<String, String> getBodyMap(){
        Map<String, String> bodyMap = httpRequest.getBodyMap();
        if(bodyMap == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid body request"));
        }
        return bodyMap;
    }

    protected HttpRequest getHttpRequest() {
        return httpRequest;
    }

    protected CustomHttpMethod getCustomHttpMethod(){
        return httpRequest.getMethod();
    }
}
