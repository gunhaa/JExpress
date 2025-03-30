package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpRequest.SimpleHttpRequest;
import simple.response.HttpResponse;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestGetHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestGetHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestGetHandler() {}

    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            HttpRequest httpRequest = new HttpRequest(simpleHttpRequest);
            HttpResponse httpResponse = new HttpResponse(simpleHttpRequest, pw);

            responseHandler.execute(httpRequest, httpResponse);

        }
    }
}