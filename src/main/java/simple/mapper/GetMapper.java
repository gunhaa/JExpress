package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.response.LambdaHandler;

import java.util.HashMap;
import java.util.Map;

public class GetMapper implements Mapper{

    private static final Mapper getMapper = new GetMapper();
    private final HashMap<String, LambdaHandler> getMap;
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
        getMap.put(url, responseSuccessHandler);
    }

    @Override
    public CustomHttpMethod getMethod() {
        return this.customHttpMethod;
    }

    @Override
    @Deprecated
    public LambdaHandler getUrl(String url) {
        // 메소드 수정 필요
        return getMap.get(url);
    }

    @Override
    public LambdaHandler getHandler(String url) {
        return getMap.get(url);
    }

    @Override
    public Map<String, LambdaHandler> getHandlers(){
        return getMap;
    }

}
