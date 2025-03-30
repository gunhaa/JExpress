package simple.response;

import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.SimpleHttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static simple.constant.ApplicationSetting.*;

public class LambdaHttpResponse {

    private final SimpleHttpRequest simpleHttpRequest;
    private final PrintWriter pw;

    public LambdaHttpResponse(SimpleHttpRequest simpleHttpRequest, PrintWriter pw) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.pw = pw;
    }

    /**
     * send default response
     */
    public void send(Object responseBody){
        ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, responseBody);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
    }


}
