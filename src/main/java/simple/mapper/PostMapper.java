package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandlerWrapper;
import simple.httpResponse.LambdaHandlerWrapper;

import java.util.Map;

public class PostMapper implements IMapper{
    @Override
    public CustomHttpMethod getMethod() {
        return null;
    }

    @Override
    public ILambdaHandlerWrapper getLambdaHandler(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers() {
        return Map.of();
    }

    @Override
    public void addUrl(String URL, ILambdaHandlerWrapper ILambdaHandlerWrapper) {

    }

    @Override
    public void addUrl(String URL, ILambdaHandlerWrapper ILambdaHandlerWrapper, Class<?> clazz) {

    }
}
