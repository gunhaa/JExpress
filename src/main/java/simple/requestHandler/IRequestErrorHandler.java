package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.HttpResponse;
import simple.httpResponse.ILambdaHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class IRequestErrorHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new IRequestErrorHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    @Override
    public void sendResponse(OutputStream outputStream, ILambdaHandler ILambdaHandler, HttpRequest httpRequest) {

        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            HttpResponse httpResponse = new HttpResponse(httpRequest, pw);

            httpResponse.sendError();

        }
    }
}
