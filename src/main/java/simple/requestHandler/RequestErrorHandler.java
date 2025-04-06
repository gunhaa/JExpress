package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.HttpResponse;
import simple.lambda.ILambdaHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestErrorHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new RequestErrorHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    @Override
    public void sendResponse(OutputStream outputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler) {

        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            HttpResponse httpResponse = new HttpResponse(httpRequest, pw);

            httpResponse.sendError();

        }
    }
}
