package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;
import simple.response.ResponseBuilder;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestGetHandler implements RequestHandler{

    public RequestGetHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream,  Response userCustomResponse, SimpleHttpRequest simpleHttpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){
            Object responseBody = userCustomResponse.getResponseSuccess().getEntity();
            ResponseBuilder hb = new ResponseBuilder(simpleHttpRequest, null, responseBody);
            StringBuilder response = hb.protocol().httpStatus().date().contentType().contentLength().server().connection().crlf().body().getResponse();
            pw.print(response);
        }
    }
}
