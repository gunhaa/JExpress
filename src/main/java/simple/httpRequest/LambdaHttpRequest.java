package simple.httpRequest;

import simple.constant.HttpStatus;
import simple.database.DBConnection;

import java.util.HashMap;

public class LambdaHttpRequest {

    private final HttpRequest httpRequest;

    public LambdaHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getQueryString(String target){
        HashMap<String, String> qs = httpRequest.getQueryString();
        String value = qs.get(target);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.NOT_FOUND_404, "QueryString not found"));
        }
        return value;
    }

    public void findEntity(Class<?> clazz, String... conditions){
//        DBConnection db = DBConnection.get
        String clazzName = clazz.getName();

    }

    public void findAll(Class<?> clazz){

    }


    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
