package simple.context;


import simple.config.ApplicationConfig;
import simple.constant.ApplicationSettingFlags;
import simple.mapper.GetMapper;
import simple.mapper.PostMapper;
import simple.middleware.MiddlewareProvider;

public class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private static final MapperResolver mapperResolver =
            new MapperResolver(GetMapper.getInstance(), PostMapper.getInstance());

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance(){
        return INSTANCE;
    }

    public static void initializeApplicationContext(){
        MiddlewareProvider middlewareProvider = MiddlewareProvider.getInstance();

        int config = ApplicationConfig.getInstance().getConfig();

        for (ApplicationSettingFlags setting : ApplicationSettingFlags.values()) {
            if ((config & setting.getBit()) == setting.getBit()) {
                middlewareProvider.execute(setting);
            }
        }
    }

    public static MapperResolver getMapperResolver(){
        return mapperResolver;
    }
}
