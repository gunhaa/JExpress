package simple.mapper;

import simple.constant.HttpMethod;
import simple.response.LambdaHandler;

import java.util.Map;

public interface Mapper {
    HttpMethod getMethod();
    LambdaHandler getUrl(String url);
    LambdaHandler getHandler(String url);
    Map<String, LambdaHandler> getHandlers();
    void addUrl(String URL, LambdaHandler responseSuccessHandler);
}
