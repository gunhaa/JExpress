package simple.response;

import simple.apiDocs.LastSentObjectHolder;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.HttpRequest;
import simple.httpRequest.LambdaHttpRequest;

import java.io.*;

import static simple.constant.ApplicationSetting.*;

public class LambdaHttpResponse {

    private final LambdaHttpRequest lambdaHttpRequest;
    private final PrintWriter pw;

    public LambdaHttpResponse(LambdaHttpRequest lambdaHttpRequest, PrintWriter pw) {
        this.lambdaHttpRequest = lambdaHttpRequest;
        this.pw = pw;
    }

    /**
     * send default response
     */
    public void send(Object responseBody, Class<?> clazz){

        // API DOCS Reflection check
        if(ServerSettingChecker.isServerEnabled(API_DOCS)){
            LastSentObjectHolder.setLastSentType(responseBody.getClass());
        }

        HttpRequest httpRequest = lambdaHttpRequest.getHttpRequest();

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, responseBody);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
    }


}
