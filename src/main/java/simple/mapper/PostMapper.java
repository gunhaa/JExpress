package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;
import simple.lambda.ILambdaHandler;
import simple.lambda.LambdaHandlerWrapper;
import simple.url.PostUrlRouterRouterTrie;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.CustomHttpMethod.POST;

public class PostMapper implements IMapper {

    private HashMap<String, LambdaHandlerWrapper> postMap;
    private final PostUrlRouterRouterTrie postUrlRouterTrie;
    private final CustomHttpMethod customHttpMethod;
    private static final IMapper postMapper = new PostMapper(POST, PostUrlRouterRouterTrie.getInstance());

    private PostMapper(CustomHttpMethod customHttpMethod, PostUrlRouterRouterTrie trie) {
        this.customHttpMethod = customHttpMethod;
        this.postUrlRouterTrie = trie;
        this.postMap = new HashMap<>();
    }

    public static IMapper getInstance() {
        return postMapper;
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    public ILambdaHandler getLambdaHandler(HttpRequest httpRequest) {
        LambdaHandlerWrapper wrapper = postUrlRouterTrie.getLambdaHandlerOrNull(httpRequest);
        return (wrapper != null) ? wrapper.unwrap() : null;
    }

    @Override
    public Map<String, LambdaHandlerWrapper> getHandlers() {
        return postMap;
    }

    @Override
    public void addUrl(String url, ILambdaHandler responseSuccessHandler) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler);
        postUrlRouterTrie.insert(url, lambdaHandlerWrapper);
        postMap.put(url, lambdaHandlerWrapper);
    }

    @Override
    public void addUrl(String url, ILambdaHandler responseSuccessHandler, Class<?> clazz) {
        LambdaHandlerWrapper lambdaHandlerWrapper = new LambdaHandlerWrapper(responseSuccessHandler, clazz);
        postUrlRouterTrie.insert(url, lambdaHandlerWrapper);
        postMap.put(url, lambdaHandlerWrapper);
    }
}
