package simple.mapper;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;

public class MapperResolver {
    private final IMapper getMapper;
    private final IMapper postMapper;

    @Deprecated
    public MapperResolver(IMapper getMapper, IMapper postMapper){
        this.getMapper = getMapper;
        this.postMapper = postMapper;
    }

    public IMapper resolveMapper(HttpRequest httpRequest){
        CustomHttpMethod httpMethod = httpRequest.getMethod();

        // todo refactoring
        // 올바르지 않은 요청의 경우 get에서 처리한다
        if (httpRequest.getMethod() == null) {
            return getMapper;
        }

        switch (httpMethod){
            case GET -> {
                return getMapper;
            }
            case POST -> {
                return postMapper;
            }
            default -> {
                return getMapper;
            }
        }
    }

}
