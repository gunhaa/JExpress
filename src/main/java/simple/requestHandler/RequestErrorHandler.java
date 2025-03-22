package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestErrorHandler implements RequestHandler {
    
    @Override
    public void sendResponse(OutputStream outputStream, SimpleHttpRequest simpleHttpRequest) {
        System.out.println("에러 발생했음");
    }
}
