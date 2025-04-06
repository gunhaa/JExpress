package simple.mapper;

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
        switch (httpRequest.getMethod()){
            case GET -> {
                return getMapper;
            }
            case POST -> {
                return postMapper;
            }
            default -> {
                // todo exception
                return getMapper;
            }
        }
    }

}
