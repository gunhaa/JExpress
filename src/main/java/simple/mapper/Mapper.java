package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.response.LambdaHandler;
import simple.response.LambdaHandlerWrapper;

import java.util.Map;

public interface Mapper {

    CustomHttpMethod getMethod();

    LambdaHandler getLambdaHandler(String url);

    Map<String, LambdaHandlerWrapper> getHandlers();

    void addUrl(String URL, LambdaHandler lambdaHandlerWrapper);

    void addUrl(String URL, LambdaHandler lambdaHandlerWrapper, Class<?> clazz);

}
