package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;

import java.io.OutputStream;

public class RequestGetHandler implements RequestHandler{

    public RequestGetHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream,  Response userCustomResponse, SimpleHttpRequest simpleHttpRequest) {
        userCustomResponse.getResponseSuccess().getDefaultResponse(outputStream, simpleHttpRequest);
    }
}
