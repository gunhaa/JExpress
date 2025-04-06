package simple.httpRequest;

import simple.constant.HttpStatus;

import java.util.HashMap;

public class LambdaHttpRequest {

    private final HttpRequest httpRequest;

    public LambdaHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getParam(String binding){
        HashMap<String, String> params = httpRequest.getParams();
        return params.get(binding);
    }

    public String getQueryString(String target){
        HashMap<String, String> qs = httpRequest.getQueryString();
        String value = qs.get(target);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid QueryString"));
        }
        return value;
    }

//    public String getBody(String target){
//        HashMap<String, String> body = httpRequest.getBodyMap();
//        String value = qs.get(target);
//        if(value == null){
//            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid QueryString"));
//        }
//        return value;
//    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
