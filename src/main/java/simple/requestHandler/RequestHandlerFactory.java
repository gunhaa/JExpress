package simple.requestHandler;

import simple.server.Response;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.Constant.HTTP_METHOD_GET;

public class RequestHandlerFactory {

    private final Map<String, RequestHandler> requestHandlers = new HashMap<>();

    private static final RequestHandlerFactory instance = new RequestHandlerFactory();

    private RequestHandlerFactory() {
        requestHandlers.put(HTTP_METHOD_GET, new RequestGetHandler());
//        requestHandlers.put(HTTP_METHOD_POST, RequestPostHandler);
//        requestHandlers.put(HTTP_METHOD_PUT, RequestPutHandler);
//        requestHandlers.put(HTTP_METHOD_DELETE, RequestDeleteHandler);
    }

    public RequestHandler getHandler(String httpMethod){
        // 에러객체 변환으로 바꿔야함
        // protocol parser 필요함
        System.out.println("getHandler에 들어온 httpMethod : " + httpMethod);
        return requestHandlers.getOrDefault(httpMethod, new RequestErrorHandler());
    }

    public static RequestHandlerFactory getInstance(){
        return instance;
    }
}
