package simple.mapper;

import simple.constant.HttpMethod;
import simple.response.ResponseHandler;

public interface Mapper {
    HttpMethod getMethod();
    ResponseHandler getUrl(String url);
    ResponseHandler getHandler(String url);
    void addUrl(String URL, ResponseHandler responseSuccessHandler);
}
