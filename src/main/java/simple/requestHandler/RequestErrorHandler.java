package simple.requestHandler;

import simple.server.Response;

import java.io.PrintWriter;

public class RequestErrorHandler implements RequestHandler {
    
    @Override
    public void handleResponse(PrintWriter out, Response userCustomResponse) {
        System.out.println("에러 발생했음");
    }
}
