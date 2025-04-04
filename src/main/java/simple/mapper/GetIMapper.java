package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpResponse.ILambdaHandlerWrapper;
import simple.httpResponse.LambdaHandlerWrapper;
import simple.url.UrlRouterTrie;

import java.util.HashMap;
import java.util.Map;

public class GetIMapper implements IMapper {

    private static final IMapper GET_I_MAPPER = new GetIMapper();
    private final HashMap<String, LambdaHandlerWrapper> getMap;
    private final UrlRouterTrie urlRouterTrie;
    private final CustomHttpMethod customHttpMethod;

    private GetIMapper() {
        this.customHttpMethod = CustomHttpMethod.GET;
        this.getMap = new HashMap<>();
        this.urlRouterTrie = new UrlRouterTrie();
    }

    public static IMapper getInstance(){
        return GET_I_MAPPER;
    }

    @Override
    public void addUrl(String url, ILambdaHandlerWrapper responseSuccessHandler) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler);
        urlRouterTrie.insert(url, lambdaHandlerWrapper);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public void addUrl(String url, ILambdaHandlerWrapper responseSuccessHandler, Class<?> clazz) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler, clazz);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    public ILambdaHandlerWrapper getLambdaHandler(String url) {
        LambdaHandlerWrapper wrapper = getMap.get(url);
        return (wrapper != null) ? wrapper.unwrap() : null;
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers(){
        return getMap;
    }

}
