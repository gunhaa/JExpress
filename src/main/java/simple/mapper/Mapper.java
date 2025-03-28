package simple.mapper;

import simple.constant.HttpMethod;
import simple.response.ResponseHandler;

public interface Mapper {
    HttpMethod getMethod();
    String getUrl();
    ResponseHandler getHandler();
    void addUrl(String URL, ResponseHandler responseSuccessHandler);
}
