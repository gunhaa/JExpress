package simple.context;

import simple.constant.CustomHttpMethod;
import simple.httpRequest.HttpRequest;
import simple.mapper.IMapper;

public class MapperResolver {
    private final IMapper getMapper;
    private final IMapper postMapper;

    protected MapperResolver(IMapper getMapper, IMapper postMapper){
        this.getMapper = getMapper;
        this.postMapper = postMapper;
    }

    public IMapper resolveMapper(HttpRequest httpRequest){
        CustomHttpMethod httpMethod = httpRequest.getMethod();

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
