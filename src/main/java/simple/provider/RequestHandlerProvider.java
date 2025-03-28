package simple.provider;

import simple.constant.HttpMethod;
import simple.httpRequest.SimpleHttpRequest;
import simple.requestHandler.*;

import java.util.HashMap;
import java.util.Map;


public class RequestHandlerProvider {

    private static final Map<HttpMethod, RequestHandler> requestHandlers = new HashMap<>();

    private static final RequestHandlerProvider INSTANCE = new RequestHandlerProvider();

    private RequestHandlerProvider() {
        requestHandlers.put(HttpMethod.GET, RequestGetHandler.getInstance());
        requestHandlers.put(HttpMethod.POST, RequestPostHandler.getInstance());
        requestHandlers.put(HttpMethod.PUT, RequestPutHandler.getInstance());
        requestHandlers.put(HttpMethod.DELETE, RequestDeleteHandler.getInstance());
    }

    public RequestHandler getHandler(SimpleHttpRequest simpleHttpRequest){

        if(!simpleHttpRequest.getErrorQueue().isEmpty()){
            return new RequestErrorHandler();
        }

//        setHandlerBySettings(simpleHttpRequest);

        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), new RequestErrorHandler());
    }

//    @Deprecated
//    private static void setHandlerBySettings(SimpleHttpRequest simpleHttpRequest){
//        if(simpleHttpRequest.getUrl().startsWith("/api-docs")){
//            requestHandlers.put(HttpMethod.GET, RequestStaticHandler.getInstance());
//        }
//    }

    public static RequestHandlerProvider getInstance(){
        return INSTANCE;
    }
}
