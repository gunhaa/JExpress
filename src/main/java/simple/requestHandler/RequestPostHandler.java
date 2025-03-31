package simple.requestHandler;

import simple.httpRequest.HttpRequest;
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
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, HttpRequest httpRequest) {
//        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
