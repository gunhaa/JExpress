package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpResponse.ILambdaHandlerWrapper;
import simple.httpResponse.LambdaHandlerWrapper;

import java.util.Map;

public interface IMapper {

    CustomHttpMethod getMethod();

    ILambdaHandlerWrapper getLambdaHandler(String url);

    Map<String, LambdaHandlerWrapper> getHandlers();

    void addUrl(String URL, ILambdaHandlerWrapper ILambdaHandlerWrapper);

    void addUrl(String URL, ILambdaHandlerWrapper ILambdaHandlerWrapper, Class<?> clazz);

}
