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
        if(ServerSettingChecker.isServerEnabled(API_DOCS)){
            LastSentObjectHolder.setLastSentType(responseBody.getClass());
        }

        HttpRequest httpRequest = lambdaHttpRequest.getHttpRequest();

        if(responseBody == null){
            // 해당 로직이 진행 가능하도록 픽스해야함
            // ErrorQueue에 밀어넣는 것으로 결과는 에러가 발생하도록 로직 수정필요
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
