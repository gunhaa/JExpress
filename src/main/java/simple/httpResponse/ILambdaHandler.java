package simple.httpResponse;

import simple.httpRequest.LambdaHttpRequest;

@FunctionalInterface
public interface ILambdaHandler {
    void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
