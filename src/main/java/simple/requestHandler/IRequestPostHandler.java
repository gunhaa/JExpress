package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandlerWrapper;

import java.io.OutputStream;

public class IRequestPostHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new IRequestPostHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private IRequestPostHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, ILambdaHandlerWrapper ILambdaHandlerWrapper, HttpRequest httpRequest) {
//        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
