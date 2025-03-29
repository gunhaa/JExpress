package simple.context;


import simple.config.ApplicationConfig;
import simple.constant.ApplicationSetting;
import simple.middleware.MiddlewareProvider;

public class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance(){
        return INSTANCE;
    }

    public static void initializeApplicationContext(){
        MiddlewareProvider middlewareProvider = MiddlewareProvider.getInstance();

        int config = ApplicationConfig.getInstance().getConfig();

        for (ApplicationSetting setting : ApplicationSetting.values()) {
            if ((config & setting.getBit()) == setting.getBit()) {
                middlewareProvider.execute(setting);
            }
        }

    }
}
