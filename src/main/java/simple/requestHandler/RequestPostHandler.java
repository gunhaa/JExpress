package simple.requestHandler;

import simple.response.Response;

import java.io.PrintWriter;

public class RequestPostHandler implements RequestHandler{
    @Override
    public void handleResponse(PrintWriter out, Response userCustomResponse) {
        userCustomResponse.getResponseSuccess().responseParser(out);
    }
}
