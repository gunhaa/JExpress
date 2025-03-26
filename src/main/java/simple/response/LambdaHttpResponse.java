package simple.response;

import simple.constant.HttpStatus;
import simple.httpRequest.SimpleHttpRequest;

import java.io.PrintWriter;

public class LambdaHttpResponse {

    private SimpleHttpRequest simpleHttpRequest;
    private PrintWriter pw;

    public LambdaHttpResponse(SimpleHttpRequest simpleHttpRequest, PrintWriter pw) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.pw = pw;
    }

    public void send(Object responseBody){
            ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, responseBody);
            StringBuilder response = responseBuilder
                    .protocol()
                    .httpStatus()
                    .date()
                    .contentType()
                    .contentLength()
                    .server()
                    .connection()
                    .crlf()
                    .body()
                    .getResponse();
            pw.print(response);
    };
}
