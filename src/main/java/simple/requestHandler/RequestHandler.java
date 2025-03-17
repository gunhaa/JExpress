package simple.requestHandler;

import simple.server.Response;

import java.io.PrintWriter;

public interface RequestHandler {
    void handleResponse(PrintWriter out, Response userCustomResponse);
}
