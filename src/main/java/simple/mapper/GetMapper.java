package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandlerWrapper;
import simple.httpResponse.LambdaHandlerWrapper;
import simple.url.GetUrlRouterTrie;

import java.util.HashMap;
import java.util.Map;

public class GetMapper implements IMapper {

    private static final IMapper GET_I_MAPPER = new GetMapper();
    private final HashMap<String, LambdaHandlerWrapper> getMap;
    private final GetUrlRouterTrie getUrlRouterTrie;
    private final CustomHttpMethod customHttpMethod;

    private GetMapper() {
        this.customHttpMethod = CustomHttpMethod.GET;
        this.getMap = new HashMap<>();
        this.getUrlRouterTrie = GetUrlRouterTrie.getInstance();
    }

    public static IMapper getInstance(){
        return GET_I_MAPPER;
    }

    @Override
    public void addUrl(String url, ILambdaHandlerWrapper responseSuccessHandler) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler);
        getUrlRouterTrie.insert(url, lambdaHandlerWrapper);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public void addUrl(String url, ILambdaHandlerWrapper responseSuccessHandler, Class<?> clazz) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler, clazz);
        getUrlRouterTrie.insert(url, lambdaHandlerWrapper);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    public ILambdaHandlerWrapper getLambdaHandler(HttpRequest httpRequest) {
        LambdaHandlerWrapper wrapper = getUrlRouterTrie.getLambdaHandlerOrNull(httpRequest);
        return (wrapper != null) ? wrapper.unwrap() : null;
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers(){
        return getMap;
    }

}
