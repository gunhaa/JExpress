package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;
import simple.response.ResponseHandler;

import java.io.OutputStream;

public interface RequestHandler {
    void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest);
}
