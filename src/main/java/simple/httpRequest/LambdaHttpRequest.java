package simple.httpRequest;

import jakarta.persistence.EntityManager;
import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.database.DBConnection;

import java.util.HashMap;
import java.util.List;

import static simple.constant.ApplicationSetting.*;

public class LambdaHttpRequest {

    private final HttpRequest httpRequest;

    public LambdaHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getQueryString(String target){
        HashMap<String, String> qs = httpRequest.getQueryString();
        String value = qs.get(target);
        System.out.println("찾은 쿼리스트링 value = " + value);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid QueryString"));
        }
        return value;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
