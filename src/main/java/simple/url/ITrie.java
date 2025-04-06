package simple.url;

import simple.httpRequest.HttpRequest;
import simple.httpResponse.LambdaHandlerWrapper;

public interface ITrie {

    UrlRouterNode getRoot();

    void insert(String path, LambdaHandlerWrapper handler);

    LambdaHandlerWrapper getLambdaHandlerOrNull(HttpRequest httpRequest);

}
