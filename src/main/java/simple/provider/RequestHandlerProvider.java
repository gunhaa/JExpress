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
    public static final String URL_FAVICON = "/favicon.ico";
    public static final String URL_HTML = ".html";
    public static final String URL_JAVASCRIPT = ".js";

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
            return RequestErrorHandler.getInstance();
        }

        if(ServerSettingChecker.isServerEnabled(API_DOCS) && simpleHttpRequest.getUrl() != null &&simpleHttpRequest.getUrl().endsWith(URL_JAVASCRIPT)){
            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
        }

        if(ServerSettingChecker.isServerEnabled(API_DOCS) && simpleHttpRequest.getUrl() != null  && simpleHttpRequest.getUrl().endsWith(URL_HTML)){
            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
        }

        if(ServerSettingChecker.isServerEnabled(API_DOCS) && simpleHttpRequest.getUrl() != null  && simpleHttpRequest.getUrl().equals(URL_FAVICON)){
            return requestHandlers.get(HttpMethod.ERROR);
        }

        return requestHandlers.getOrDefault(simpleHttpRequest.getMethod(), RequestErrorHandler.getInstance());
    }

    public static RequestHandlerProvider getInstance(){
        return INSTANCE;
    }
}
