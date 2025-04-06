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

    public IMapper resolveMapperOrNull(HttpRequest httpRequest){
        CustomHttpMethod httpMethod = httpRequest.getMethod();

        // todo refactoring
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
