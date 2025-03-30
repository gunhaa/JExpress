package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.LambdaHandler;

import java.io.OutputStream;

public class RequestPutHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestPutHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestPutHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, SimpleHttpRequest simpleHttpRequest) {

    }
}
