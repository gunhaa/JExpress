package simple.response;

import simple.httpRequest.SimpleHttpRequest;

@FunctionalInterface
public interface ResponseSuccessHandler {
    void success(SimpleHttpRequest simpleHttpRequest, SimpleHttpResponse simpleHttpResponse);
}
