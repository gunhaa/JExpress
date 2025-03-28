//package simple.factory;
//
//import simple.constant.ApplicationSetting;
//import simple.constant.HttpMethod;
//import simple.httpRequest.SimpleHttpRequest;
//import simple.requestHandler.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ApplicationContextFactory {
//
//    private final Map<HttpMethod, RequestHandler> requestHandlers = new HashMap<>();
//
//    private static final ApplicationContextFactory INSTANCE = new ApplicationContextFactory();
//
//    private ApplicationContextFactory() {
//        requestHandlers.put(HttpMethod.GET, new RequestGetHandler());
//        requestHandlers.put(HttpMethod.POST, new RequestPostHandler());
//        requestHandlers.put(HttpMethod.PUT, new RequestPutHandler());
//        requestHandlers.put(HttpMethod.DELETE, new RequestDeleteHandler());
//    }
//
//    public ApplicationContextFactory getHandler(ApplicationSetting applicationSetting){
//
//        if(!simpleHttpRequest.getErrorQueue().isEmpty()){
//            return new RequestErrorHandler();
//        }
//        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), new RequestErrorHandler());
//    }
//
//    public static ApplicationContextFactory getInstance(){
//        return INSTANCE;
//    }
//}
