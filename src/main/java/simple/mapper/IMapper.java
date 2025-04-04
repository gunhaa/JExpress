package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHandlerWrapper;

import java.util.Map;

public interface IMapper {

    CustomHttpMethod getMethod();

    ILambdaHandler getLambdaHandler(String url);

    Map<String, LambdaHandlerWrapper> getHandlers();

    void addUrl(String URL, ILambdaHandler ILambdaHandlerWrapper);

    void addUrl(String URL, ILambdaHandler ILambdaHandlerWrapper, Class<?> clazz);

}
