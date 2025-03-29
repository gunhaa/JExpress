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

            ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, errorStatus, errorStatus);

            responseBuilder = responseBuilder.getDefaultResponse();

            if(ServerSettingChecker.isServerEnabled(CORS)){
                responseBuilder.cors();
            }

            StringBuilder response = responseBuilder.getResponse();
            pw.print(response);

        }
    }
}
