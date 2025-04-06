package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;

import java.io.OutputStream;

public class RequestDeleteHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new RequestDeleteHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestDeleteHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler) {

    }
}
