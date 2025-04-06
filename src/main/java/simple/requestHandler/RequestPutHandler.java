package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.lambda.ILambdaHandler;

import java.io.OutputStream;

public class RequestPutHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new RequestPutHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestPutHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler) {

    }
}
