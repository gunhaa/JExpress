package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.LambdaHandler;

import java.io.OutputStream;

public class RequestPostHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestPostHandler();

    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestPostHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, SimpleHttpRequest simpleHttpRequest) {
//        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
