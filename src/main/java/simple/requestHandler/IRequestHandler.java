package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;

import java.io.OutputStream;

public interface IRequestHandler {
    void sendResponse(OutputStream clientOutputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler);
}
