package simple.factory;

import simple.constant.HttpMethod;
import simple.httpRequest.SimpleHttpRequest;
import simple.requestHandler.*;

import java.util.HashMap;
import java.util.Map;


public class RequestHandlerFactory {

    private final Map<HttpMethod, RequestHandler> requestHandlers = new HashMap<>();

    private static final RequestHandlerFactory INSTANCE = new RequestHandlerFactory();

    private RequestHandlerFactory() {
        requestHandlers.put(HttpMethod.GET, new RequestGetHandler());
        requestHandlers.put(HttpMethod.POST, new RequestPostHandler());
        requestHandlers.put(HttpMethod.PUT, new RequestPutHandler());
        requestHandlers.put(HttpMethod.DELETE, new RequestDeleteHandler());
    }

    public RequestHandler getHandler(SimpleHttpRequest simpleHttpRequest){

        if(!simpleHttpRequest.getErrorQueue().isEmpty()){
            return new RequestErrorHandler();
        }
        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), new RequestErrorHandler());
    }

    public static RequestHandlerFactory getInstance(){
        return INSTANCE;
    }
}
