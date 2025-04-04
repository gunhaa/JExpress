package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandlerWrapper;

import java.io.OutputStream;

public class IRequestPutHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new IRequestPutHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private IRequestPutHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, ILambdaHandlerWrapper ILambdaHandlerWrapper, HttpRequest httpRequest) {

    }
}
