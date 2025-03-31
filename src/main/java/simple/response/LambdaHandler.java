package simple.response;

import simple.httpRequest.LambdaHttpRequest;

@FunctionalInterface
public interface LambdaHandler {
    void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
