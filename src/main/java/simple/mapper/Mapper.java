package simple.mapper;

import simple.constant.HttpMethod;
import simple.response.LambdaHandler;

public interface Mapper {
    HttpMethod getMethod();
    LambdaHandler getUrl(String url);
    LambdaHandler getHandler(String url);
    void addUrl(String URL, LambdaHandler responseSuccessHandler);
}
