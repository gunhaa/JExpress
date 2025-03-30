package simple.response;

import simple.httpRequest.HttpRequest;

@FunctionalInterface
public interface LambdaHandler {
    void execute(HttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
