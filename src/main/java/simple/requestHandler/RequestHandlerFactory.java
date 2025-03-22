package simple.requestHandler;

import simple.httpMethod.HttpMethod;
import simple.httpRequest.SimpleHttpRequest;

import java.util.HashMap;
import java.util.Map;


public class RequestHandlerFactory {

    private final Map<HttpMethod, RequestHandler> requestHandlers = new HashMap<>();

    private static final RequestHandlerFactory INSTANCE = new RequestHandlerFactory();

    private RequestHandlerFactory() {
        requestHandlers.put(HttpMethod.GET, new RequestGetHandler());
//        requestHandlers.put(HTTP_METHOD_POST, RequestPostHandler);
//        requestHandlers.put(HTTP_METHOD_PUT, RequestPutHandler);
//        requestHandlers.put(HTTP_METHOD_DELETE, RequestDeleteHandler);
    }

    public RequestHandler getHandler(SimpleHttpRequest simpleHttpRequest){
        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), new RequestErrorHandler());
    }

    public static RequestHandlerFactory getInstance(){
        return INSTANCE;
    }
}
