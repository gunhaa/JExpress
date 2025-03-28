package simple.provider;

import simple.constant.ApplicationSetting;
import simple.constant.HttpMethod;
import simple.context.ApplicationConfig;
import simple.httpRequest.SimpleHttpRequest;
import simple.requestHandler.*;

import java.util.HashMap;
import java.util.Map;


public class RequestHandlerProvider {

    private final Map<HttpMethod, RequestHandler> requestHandlers;
    private static final RequestHandlerProvider INSTANCE = new RequestHandlerProvider();

    private RequestHandlerProvider() {
        this.requestHandlers = new HashMap<>();
        requestHandlers.put(HttpMethod.GET, RequestGetHandler.getInstance());
        requestHandlers.put(HttpMethod.POST, RequestPostHandler.getInstance());
        requestHandlers.put(HttpMethod.PUT, RequestPutHandler.getInstance());
        requestHandlers.put(HttpMethod.DELETE, RequestDeleteHandler.getInstance());
        requestHandlers.put(HttpMethod.EXCEPTION_STATIC, RequestStaticHandler.getInstance());
    }

    public RequestHandler getHandler(SimpleHttpRequest simpleHttpRequest){

        if(!simpleHttpRequest.getErrorQueue().isEmpty()){
            return new RequestErrorHandler();
        }

        ApplicationConfig config = ApplicationConfig.getInstance();

//        if(ApplicationSetting.API_DOCS.isEnabled(config.getApplicationConfig())) && URL이 api-docs 라면){
//            System.out.println("설정이 켜진체 요청이 왔음");
//            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
//

        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), new RequestErrorHandler());
    }

    public static RequestHandlerProvider getInstance(){
        return INSTANCE;
    }
}
