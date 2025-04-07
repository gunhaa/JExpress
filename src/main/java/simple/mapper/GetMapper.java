package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;
import simple.lambda.ILambdaHandler;
import simple.lambda.LambdaHandlerWrapper;
import simple.url.GetUrlRouterTrie;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.CustomHttpMethod.GET;

public class GetMapper implements IMapper {

    private HashMap<String, LambdaHandlerWrapper> getMap;
    private final GetUrlRouterTrie getUrlRouterTrie;
    private final CustomHttpMethod customHttpMethod;
    private static final IMapper getMapper = new GetMapper(GET, GetUrlRouterTrie.getInstance());

    private GetMapper(CustomHttpMethod customHttpMethod,GetUrlRouterTrie trie) {
        this.customHttpMethod = customHttpMethod;
        this.getUrlRouterTrie = trie;
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
