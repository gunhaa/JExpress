package simple.mapper;

import simple.constant.HttpMethod;
import simple.response.ResponseHandler;

import java.util.HashMap;

public class GetMapper implements Mapper{

    private static final Mapper getMapper = new GetMapper();
    private final HashMap<String, ResponseHandler> getMap;
    private final HttpMethod httpMethod;

    private GetMapper() {
        this.httpMethod = HttpMethod.GET;
        this.getMap = new HashMap<>();
    }

    public static Mapper getInstance(){
        return getMapper;
    }

    @Override
    public void addUrl(String url, ResponseHandler responseSuccessHandler) {
        getMap.put(url, responseSuccessHandler);
    }

    @Override
    public HttpMethod getMethod() {
        return null;
    }

    @Override
    @Deprecated
    public ResponseHandler getUrl(String url) {
        // 메소드 수정 필요
        return getMap.get(url);
    }

    @Override
    public ResponseHandler getHandler(String url) {
        return getMap.get(url);
    }


}
