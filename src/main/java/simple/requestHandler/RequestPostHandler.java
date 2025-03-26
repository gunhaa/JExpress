package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestPostHandler implements RequestHandler{
    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {
//        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
