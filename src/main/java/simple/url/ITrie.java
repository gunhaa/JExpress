package simple.url;

import simple.httpRequest.HttpRequest;
import simple.lambda.LambdaHandlerWrapper;

public interface ITrie {

    UrlRouterNode getRoot();

    void insert(String path, LambdaHandlerWrapper handler);

    LambdaHandlerWrapper getLambdaHandlerOrNull(HttpRequest httpRequest);

}
