package simple.context;


import simple.config.ApplicationConfig;
import simple.constant.ApplicationSettingFlags;
import simple.constant.ServerSettingChecker;
import simple.mapper.IMapper;
import simple.middleware.MiddlewareProvider;

import static simple.constant.ApplicationSettingFlags.DB_H2;
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
            applicationConfig.unRegisterConfig(DB_H2);
            config = applicationConfig.getConfig();
        }

        if(ServerSettingChecker.isH2AndMySQLDisabled(config)){
            applicationConfig.registerConfig(DB_H2);
            config = applicationConfig.getConfig();
        }

        for (ApplicationSettingFlags setting : ApplicationSettingFlags.values()) {
            if ((config & setting.getBit()) == setting.getBit()) {
                System.out.println("현재 설정된 세팅 : " + setting);
                this.middlewareProvider.execute(setting);
            }
        }
    }

    public MapperResolver getMapperResolver(){
        return this.mapperResolver;
    }

}
