package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHandlerWrapper;
import simple.url.GetUrlRouterTrie;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.ApplicationSettingFlags.API_DOCS;

public class GetMapper implements IMapper {

    private static final IMapper getMapper = new GetMapper();
    private HashMap<String, LambdaHandlerWrapper> getMap;
    private final GetUrlRouterTrie getUrlRouterTrie;
    private final CustomHttpMethod customHttpMethod;

    private GetMapper() {
        this.customHttpMethod = CustomHttpMethod.GET;
        this.getUrlRouterTrie = GetUrlRouterTrie.getInstance();
        this.getMap = new HashMap<>();
    }

    public static IMapper getInstance() {
        return getMapper;
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    public ILambdaHandler getLambdaHandler(HttpRequest httpRequest) {
        LambdaHandlerWrapper wrapper = getUrlRouterTrie.getLambdaHandlerOrNull(httpRequest);
        return (wrapper != null) ? wrapper.unwrap() : null;
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers() {
        return getMap;
    }

    @Override
    public void addUrl(String url, ILambdaHandler responseSuccessHandler) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler);
        getUrlRouterTrie.insert(url, lambdaHandlerWrapper);
        getMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public void addUrl(String url, ILambdaHandler responseSuccessHandler, Class<?> clazz) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler, clazz);
        getUrlRouterTrie.insert(url, lambdaHandlerWrapper);
        getMap.put(url, lambdaHandlerWrapper);
    }

}
