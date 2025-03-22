package simple.requestHandler;

import simple.response.Response;

import java.io.PrintWriter;

public interface RequestHandler {
    void handleResponse(PrintWriter out, Response userCustomResponse);
}
