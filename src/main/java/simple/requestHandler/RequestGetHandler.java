package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestGetHandler implements RequestHandler{

    public RequestGetHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, SimpleHttpRequest simpleHttpRequest) {
//        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
