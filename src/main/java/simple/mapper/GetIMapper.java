package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.URL;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHandlerWrapper;

import java.util.HashMap;
import java.util.Map;

public class GetIMapper implements IMapper {

    private static final IMapper GET_I_MAPPER = new GetIMapper();
    private final HashMap<String, LambdaHandlerWrapper> getMap;
    private final CustomHttpMethod customHttpMethod;

    private GetIMapper() {
        this.customHttpMethod = CustomHttpMethod.GET;
        this.getMap = new HashMap<>();
    }

    public static IMapper getInstance(){
        return GET_I_MAPPER;
    }

    @Override
    public void addUrl(String url, ILambdaHandler responseSuccessHandler) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public void addUrl(String url, ILambdaHandler responseSuccessHandler, Class<?> clazz) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler, clazz);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    public ILambdaHandler getLambdaHandler(String url) {
        LambdaHandlerWrapper wrapper = getMap.get(url);
        return (wrapper != null) ? wrapper.unwrap() : null;
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers(){
        return getMap;
    }

}
