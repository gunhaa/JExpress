package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHandlerWrapper;

import java.util.Map;

public interface IMapper {

    CustomHttpMethod getMethod();

    ILambdaHandler getLambdaHandler(HttpRequest httpRequest);

    Map<String, LambdaHandlerWrapper> getHandlers();

    void addUrl(String URL, ILambdaHandler ILambdaHandler);

    void addUrl(String URL, ILambdaHandler ILambdaHandler, Class<?> clazz);

}
