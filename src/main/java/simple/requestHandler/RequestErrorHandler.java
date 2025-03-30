package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.HttpResponse;
import simple.response.LambdaHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestErrorHandler implements RequestHandler {

    private static final RequestHandler INSTANCE = new RequestErrorHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    @Override
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, SimpleHttpRequest simpleHttpRequest) {

        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            HttpResponse httpResponse = new HttpResponse(simpleHttpRequest, pw);

            httpResponse.sendError();

        }
    }
}
