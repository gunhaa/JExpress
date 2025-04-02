package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.response.LambdaHandler;
import simple.response.LambdaHandlerWrapper;

import java.util.HashMap;
import java.util.Map;

public class GetMapper implements Mapper{

    private static final Mapper getMapper = new GetMapper();
    private final HashMap<String, LambdaHandlerWrapper> getMap;
    private final CustomHttpMethod customHttpMethod;

    private GetMapper() {
        this.customHttpMethod = CustomHttpMethod.GET;
        this.getMap = new HashMap<>();
    }

    public static Mapper getInstance(){
        return getMapper;
    }

    @Override
    public void addUrl(String url, LambdaHandler responseSuccessHandler) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public void addUrl(String url, LambdaHandler responseSuccessHandler, Class<?> clazz) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler, clazz);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    public LambdaHandlerWrapper getHandler(String url) {
        return getMap.get(url);
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers(){
        return getMap;
    }

}
