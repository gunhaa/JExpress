package simple.provider;

import simple.constant.CustomHttpMethod;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.HttpRequest;
import simple.requestHandler.*;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.ApplicationSetting.*;


public class RequestHandlerProvider {

    private final Map<CustomHttpMethod, IRequestHandler> requestHandlers;
    private static final RequestHandlerProvider INSTANCE = new RequestHandlerProvider();
    public static final String URL_FAVICON = "/favicon.ico";
    public static final String URL_HTML = ".html";
    public static final String URL_JAVASCRIPT = ".js";

    private RequestHandlerProvider() {
        this.requestHandlers = new HashMap<>();
        requestHandlers.put(CustomHttpMethod.GET, IRequestGetHandler.getInstance());
        requestHandlers.put(CustomHttpMethod.POST, IRequestPostHandler.getInstance());
        requestHandlers.put(CustomHttpMethod.PUT, IRequestPutHandler.getInstance());
        requestHandlers.put(CustomHttpMethod.DELETE, IRequestDeleteHandler.getInstance());
        requestHandlers.put(CustomHttpMethod.EXCEPTION_STATIC, IRequestStaticHandler.getInstance());
        requestHandlers.put(CustomHttpMethod.ERROR, IRequestErrorHandler.getInstance());
    }

    public IRequestHandler getHandler(HttpRequest httpRequest) {

        if (!httpRequest.getErrorQueue().isEmpty()) {
            return IRequestErrorHandler.getInstance();
        }

        if (ServerSettingChecker.isServerEnabled(API_DOCS) && httpRequest.getUrl() != null) {
            if (httpRequest.getUrl().endsWith(URL_JAVASCRIPT)) {
                return requestHandlers.get(CustomHttpMethod.EXCEPTION_STATIC);
            }

            if (httpRequest.getUrl().endsWith(URL_HTML)) {
                return requestHandlers.get(CustomHttpMethod.EXCEPTION_STATIC);
            }

            if (httpRequest.getUrl().equals(URL_FAVICON)) {
                return requestHandlers.get(CustomHttpMethod.EXCEPTION_STATIC);
            }
        }

//        if(ServerSettingChecker.isServerEnabled(API_DOCS) && httpRequest.getUrl() != null && httpRequest.getUrl().endsWith(URL_JAVASCRIPT)){
//            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
//        }
//
//        if(ServerSettingChecker.isServerEnabled(API_DOCS) && httpRequest.getUrl() != null  && httpRequest.getUrl().endsWith(URL_HTML)){
//            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
//        }
//
//        if(ServerSettingChecker.isServerEnabled(API_DOCS) && httpRequest.getUrl() != null  && httpRequest.getUrl().equals(URL_FAVICON)){
//            return requestHandlers.get(HttpMethod.EXCEPTION_STATIC);
//        }

        return requestHandlers.getOrDefault(httpRequest.getMethod(), IRequestErrorHandler.getInstance());
    }

    public static RequestHandlerProvider getInstance() {
        return INSTANCE;
    }
}
