package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface RequestHandler {
    void sendResponse(OutputStream outputStream, Response response);
}
