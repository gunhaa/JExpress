package simple.response;

import simple.apiDocs.LastSentObjectHolder;
import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
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
    public void send(Object responseBody){

        // API DOCS Reflection check
        if(ServerSettingChecker.isServerEnabled(API_DOCS) && responseBody instanceof LastSentObjectHolder){
            LastSentObjectHolder.setLastSentType(responseBody.getClass());
            return;
        }

        HttpRequest httpRequest = lambdaHttpRequest.getHttpRequest();

        if(responseBody == null){
            responseBody = new ErrorStatus(HttpStatus.NOT_FOUND_404, "can't find entity");
        }

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, responseBody);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
    }

    /**
     * use Api docs
     */
    public void send(Object responseBody, Class<?> clazz){

        // API DOCS Reflection check
        if(ServerSettingChecker.isServerEnabled(API_DOCS) && responseBody instanceof LastSentObjectHolder){
            LastSentObjectHolder.setLastSentType(responseBody.getClass());
            return;
        }

        HttpRequest httpRequest = lambdaHttpRequest.getHttpRequest();

        if(responseBody == null){
            responseBody = new ErrorStatus(HttpStatus.NOT_FOUND_404, "can't find entity");
        }

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, responseBody);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
    }

}
