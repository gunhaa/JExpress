package simple.response;

import simple.apiDocs.LastSentObjectHolder;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.HttpRequest;

import java.io.*;

import static simple.constant.ApplicationSetting.*;

public class LambdaHttpResponse {

    private final HttpRequest httpRequest;
    private final PrintWriter pw;

    public LambdaHttpResponse(HttpRequest httpRequest, PrintWriter pw) {
        this.httpRequest = httpRequest;
        this.pw = pw;
    }

    /**
     * send default response
     */
    public void send(Object responseBody){

        // API DOCS Reflection check
        if(ServerSettingChecker.isServerEnabled(API_DOCS)){
            LastSentObjectHolder.setLastSentType(responseBody.getClass());
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
