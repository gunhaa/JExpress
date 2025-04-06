package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;

import java.io.OutputStream;

public class IRequestPostHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new IRequestPostHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private IRequestPostHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, ILambdaHandler ILambdaHandler, HttpRequest httpRequest) {
        System.out.println("여기까지 잘왔음");
    }
}
