package simple.mapper;

public class MapperResolver {
    private final IMapper getMapper;
    private final IMapper postMapper;
    private static final MapperResolver INSTANCE = new MapperResolver(GetMapper.getInstance(), PostMapper.getInstance());

    // cant new
    public MapperResolver(IMapper getMapper, IMapper postMapper){
        this.getMapper = getMapper;
        this.postMapper = postMapper;
    }

    public static MapperResolver getInstance(){
        return INSTANCE;
    }

}
