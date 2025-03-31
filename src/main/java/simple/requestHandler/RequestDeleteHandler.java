package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.response.LambdaHandler;

import java.io.OutputStream;

public class RequestDeleteHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestDeleteHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestDeleteHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, HttpRequest httpRequest) {

    }
}
