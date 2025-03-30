package simple.response;

import simple.httpRequest.HttpRequest;

@FunctionalInterface
public interface ResponseHandler {
    void execute(HttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
