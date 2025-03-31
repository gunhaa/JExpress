package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.response.LambdaHandler;

import java.io.OutputStream;

public interface RequestHandler {
    void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, HttpRequest httpRequest);
}
