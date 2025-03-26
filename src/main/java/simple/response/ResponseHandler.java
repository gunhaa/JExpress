package simple.response;

import simple.httpRequest.LambdaHttpRequest;

@FunctionalInterface
public interface ResponseHandler {
    void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
