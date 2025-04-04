package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;

import java.io.OutputStream;

public class IRequestDeleteHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new IRequestDeleteHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private IRequestDeleteHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, ILambdaHandler ILambdaHandler, HttpRequest httpRequest) {

    }
}
