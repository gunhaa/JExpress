package simple.requestHandler;

import simple.server.Response;

import java.io.PrintWriter;

public class RequestGetHandler implements RequestHandler{
    @Override
    public void handleResponse(PrintWriter out, Response userCustomResponse) {
        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
