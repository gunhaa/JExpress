package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.response.LambdaHandler;

import java.util.Map;

public interface Mapper {
    CustomHttpMethod getMethod();
    LambdaHandler getUrl(String url);
    LambdaHandler getHandler(String url);
    Map<String, LambdaHandler> getHandlers();
    void addUrl(String URL, LambdaHandler responseSuccessHandler);
}
