package simple.requestHandler;

import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;
import simple.httpRequest.SimpleHttpRequest;
import simple.response.HttpResponse;
import simple.response.LambdaHttpResponse;
import simple.response.LambdaHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestGetHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestGetHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestGetHandler() {}

    @Override
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, SimpleHttpRequest simpleHttpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            // 등록되지 않은 요청
            if(lambdaHandler == null){
                HttpResponse httpResponse = new HttpResponse(simpleHttpRequest, pw);
                ErrorStatus errorStatus = new ErrorStatus(HttpStatus.NOT_FOUND_404, "Not valid URL");
                httpResponse.sendError(errorStatus);
                return;
            }

            HttpRequest httpRequest = new HttpRequest(simpleHttpRequest);
            LambdaHttpResponse lambdaHttpResponse = new LambdaHttpResponse(simpleHttpRequest, pw);
            lambdaHandler.execute(httpRequest, lambdaHttpResponse);

        }
    }
}