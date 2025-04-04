package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandlerWrapper;

import java.io.OutputStream;

public interface IRequestHandler {
    void sendResponse(OutputStream outputStream, ILambdaHandlerWrapper ILambdaHandlerWrapper, HttpRequest httpRequest);
}
