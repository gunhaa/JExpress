package simple.context;


import simple.config.ApplicationConfig;
import simple.constant.ApplicationSettingFlags;
import simple.constant.ServerSettingChecker;
import simple.mapper.IMapper;
import simple.middleware.MiddlewareProvider;

import static simple.constant.ApplicationSettingFlags.DB_MYSQL;


public class ApplicationContext {

    private static ApplicationContext INSTANCE;

    private final ApplicationConfig applicationConfig;
    private final MapperResolver mapperResolver;
    private final MiddlewareProvider middlewareProvider;

    private ApplicationContext(MapperResolver mapperResolver, MiddlewareProvider middlewareProvider, ApplicationConfig applicationConfig) {
        this.mapperResolver = mapperResolver;
        this.middlewareProvider = middlewareProvider;
        this.applicationConfig = applicationConfig;
    }

    public static void initialize(IMapper getMapper, IMapper postMapper, MiddlewareProvider middlewareProvider, ApplicationConfig applicationConfig) {
        MapperResolver resolver = new MapperResolver(getMapper, postMapper);
        INSTANCE = new ApplicationContext(resolver, middlewareProvider, applicationConfig);
    }

    public static ApplicationContext getInstance(){
        if (INSTANCE == null) {
            throw new IllegalStateException("not initialized");
        }
        return INSTANCE;
    }

    public void initializeMiddleWare(){

        int config = applicationConfig.getConfig();

        if(ServerSettingChecker.isH2AndMySQLEnabled(config)){
            //todo 현재 테스트용으로 두 설정 모두 사용시 MySQL 설정 off, 정상은 h2 off
            applicationConfig.unRegisterConfig(DB_MYSQL);
        }

        for (ApplicationSettingFlags setting : ApplicationSettingFlags.values()) {
            if ((config & setting.getBit()) == setting.getBit()) {
                this.middlewareProvider.execute(setting);
            }
        }
    }

    public MapperResolver getMapperResolver(){
        return this.mapperResolver;
    }

}
