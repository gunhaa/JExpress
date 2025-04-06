package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHandlerWrapper;
import simple.url.PostUrlRouterTrie;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.ApplicationSettingFlags.API_DOCS;

public class PostMapper implements IMapper {

    private static final IMapper postMapper = new PostMapper();
    private HashMap<String, LambdaHandlerWrapper> postMap;
    private final PostUrlRouterTrie postUrlRouterTrie;
    private final CustomHttpMethod customHttpMethod;

    private PostMapper() {
        this.customHttpMethod = CustomHttpMethod.POST;
        this.postUrlRouterTrie = PostUrlRouterTrie.getInstance();
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
