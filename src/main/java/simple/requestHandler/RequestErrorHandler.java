package simple.requestHandler;

import simple.httpRequest.ErrorStatus;
import simple.httpRequest.SimpleHttpRequest;
import simple.response.ResponseBuilder;
import simple.response.Response;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Optional;

public class RequestErrorHandler implements RequestHandler {
    
    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {
//        System.out.println("에러 발생했음");
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            Optional<ErrorStatus> optionalErrorStatus = Optional.ofNullable(simpleHttpRequest.getErrorQueue().peek());
            ErrorStatus errorStatus = optionalErrorStatus.orElse(ErrorStatus.getDefaultErrorStatus());

            ResponseBuilder hb = new ResponseBuilder(simpleHttpRequest, simpleHttpRequest.getErrorQueue(), errorStatus);
            StringBuilder res = hb.protocol().httpStatus().date().contentType().contentLength().server().connection().crlf().body().getResponse();
            pw.print(res);
        }
    }
}
