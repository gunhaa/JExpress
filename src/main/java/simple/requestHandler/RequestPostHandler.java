package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestPostHandler implements RequestHandler{
    @Override
    public void sendResponse(OutputStream outputStream,  Response response, SimpleHttpRequest simpleHttpRequest) {
//        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
