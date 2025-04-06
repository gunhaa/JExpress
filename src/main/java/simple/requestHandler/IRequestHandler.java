package simple.requestHandler;

import simple.httpRequest.HttpRequest;
import simple.lambda.ILambdaHandler;

import java.io.OutputStream;

public interface IRequestHandler {
    void sendResponse(OutputStream clientOutputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler);
}
