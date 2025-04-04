package simple.httpResponse;

import simple.httpRequest.LambdaHttpRequest;

@FunctionalInterface
public interface ILambdaHandlerWrapper {
    void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
