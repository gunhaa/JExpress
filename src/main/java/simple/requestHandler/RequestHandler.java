package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.LambdaHandler;

import java.io.OutputStream;

public interface RequestHandler {
    void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, SimpleHttpRequest simpleHttpRequest);
}
