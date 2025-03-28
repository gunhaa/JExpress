package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.ResponseHandler;

import java.io.OutputStream;

public class RequestDeleteHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestDeleteHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestDeleteHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {

    }
}
