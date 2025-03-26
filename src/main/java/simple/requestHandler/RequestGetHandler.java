package simple.requestHandler;

import simple.httpRequest.LambdaHttpRequest;
import simple.httpRequest.SimpleHttpRequest;
import simple.response.LambdaHttpResponse;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestGetHandler implements RequestHandler{

    public RequestGetHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            LambdaHttpRequest lambdaHttpRequest = new LambdaHttpRequest(simpleHttpRequest);
            LambdaHttpResponse lambdaHttpResponse = new LambdaHttpResponse(simpleHttpRequest, pw);

            responseHandler.execute(lambdaHttpRequest, lambdaHttpResponse);

        }
    }
}