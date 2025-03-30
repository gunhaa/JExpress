package simple.provider;

import simple.constant.ApplicationSetting;
import simple.constant.HttpMethod;
import simple.config.ApplicationConfig;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.SimpleHttpRequest;
import simple.requestHandler.*;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.ApplicationSetting.*;


public class RequestHandlerProvider {

    private final Map<HttpMethod, RequestHandler> requestHandlers;
    private static final RequestHandlerProvider INSTANCE = new RequestHandlerProvider();
    private static final String URL_API_DOCS = "/api-docs";
    private static final String URL_FAVICON = "/favicon.ico";

    private RequestHandlerProvider() {
        this.requestHandlers = new HashMap<>();
        requestHandlers.put(HttpMethod.GET, RequestGetHandler.getInstance());
        requestHandlers.put(HttpMethod.POST, RequestPostHandler.getInstance());
        requestHandlers.put(HttpMethod.PUT, RequestPutHandler.getInstance());
        requestHandlers.put(HttpMethod.DELETE, RequestDeleteHandler.getInstance());
        requestHandlers.put(HttpMethod.EXCEPTION_STATIC, RequestStaticHandler.getInstance());
        requestHandlers.put(HttpMethod.ERROR, RequestErrorHandler.getInstance());
    }

    public RequestHandler getHandler(SimpleHttpRequest simpleHttpRequest){

        if(!simpleHttpRequest.getErrorQueue().isEmpty()){
            return new RequestErrorHandler();
        }

        System.out.println("이곳이 실행되는 설정 : " + ServerSettingChecker.isServerEnabled(API_DOCS));
        System.out.println("얻은 URL " + simpleHttpRequest.getUrl());

        if(ServerSettingChecker.isServerEnabled(API_DOCS) && simpleHttpRequest.getUrl().equals(URL_API_DOCS)){
            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
        }

        if(ServerSettingChecker.isServerEnabled(API_DOCS) && simpleHttpRequest.getUrl().equals(URL_FAVICON)){
            return requestHandlers.get(HttpMethod.ERROR);
        }

        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), RequestErrorHandler.getInstance());
    }

    public static RequestHandlerProvider getInstance(){
        return INSTANCE;
    }
}
