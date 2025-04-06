package simple.lambda;

import simple.apiDocs.LastSentObjectHolder;
import simple.constant.CustomHttpMethod;
import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.ResponseBuilder;

import java.io.PrintWriter;

import static simple.constant.ApplicationSettingFlags.API_DOCS;
import static simple.constant.ApplicationSettingFlags.CORS;

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
            if(lambdaHttpRequest.getCustomHttpMethod() == CustomHttpMethod.GET){
                responseBody = new ErrorStatus(HttpStatus.NOT_FOUND_404, "cant find entity");
            }

            if(lambdaHttpRequest.getCustomHttpMethod() == CustomHttpMethod.POST){
                responseBody = new ErrorStatus(HttpStatus.BAD_REQUEST_400, "cant create entity");
            }

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
    @Deprecated
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
