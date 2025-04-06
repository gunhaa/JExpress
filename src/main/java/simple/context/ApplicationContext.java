package simple.context;


import simple.config.ApplicationConfig;
import simple.constant.ApplicationSettingFlags;
import simple.mapper.GetMapper;
import simple.mapper.IMapper;
import simple.mapper.PostMapper;
import simple.middleware.MiddlewareProvider;

public class ApplicationContext {

    private static ApplicationContext INSTANCE;

    private final MapperResolver mapperResolver;
    private final MiddlewareProvider middlewareProvider;

    private ApplicationContext(MapperResolver mapperResolver, MiddlewareProvider middlewareProvider) {
        this.mapperResolver = mapperResolver;
        this.middlewareProvider = middlewareProvider;
    }

    public static void initialize(IMapper getMapper, IMapper postMapper, MiddlewareProvider middlewareProvider) {
        MapperResolver resolver = new MapperResolver(getMapper, postMapper);
        INSTANCE = new ApplicationContext(resolver, middlewareProvider);
    }

    public static ApplicationContext getInstance(){
        if (INSTANCE == null) {
            throw new IllegalStateException("not initialized");
        }
        return INSTANCE;
    }

    public static void initializeMiddleWare(){
        MiddlewareProvider middlewareProvider = MiddlewareProvider.getInstance();

        int config = ApplicationConfig.getInstance().getConfig();

        for (ApplicationSettingFlags setting : ApplicationSettingFlags.values()) {
            if ((config & setting.getBit()) == setting.getBit()) {
                middlewareProvider.execute(setting);
            }
        }
    }

    public MapperResolver getMapperResolver(){
        return this.mapperResolver;
    }

}
