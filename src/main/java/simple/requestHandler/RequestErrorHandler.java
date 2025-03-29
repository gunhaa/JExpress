package simple.requestHandler;

import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.SimpleHttpRequest;
import simple.response.ResponseBuilder;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Optional;

import static simple.constant.ApplicationSetting.CORS;

public class RequestErrorHandler implements RequestHandler {
    
    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {

        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            Optional<ErrorStatus> optionalErrorStatus = Optional.ofNullable(simpleHttpRequest.getErrorQueue().peek());
            ErrorStatus errorStatus = optionalErrorStatus.orElse(ErrorStatus.getDefaultErrorStatus());

//            ResponseBuilder hb = new ResponseBuilder(simpleHttpRequest, simpleHttpRequest.getErrorQueue(), errorStatus);
//            StringBuilder res = hb.protocol().httpStatus().date().contentType().contentLength().server().connection().crlf().jsonBody().getResponse();
//            pw.print(res);

            ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, errorStatus, errorStatus);

            ResponseBuilder responseBuilding = responseBuilder.getDefaultResponse();

            if(ServerSettingChecker.isServerEnabled(CORS)){
                responseBuilding.cors();
            }

            StringBuilder response = responseBuilding.getResponse();
            pw.print(response);

        }
    }
}
