package simple.config;

import simple.constant.ApplicationSetting;
import simple.middleware.Cors;

public class ApplicationConfig {

    private static final ApplicationConfig INSTANCE = new ApplicationConfig();

    private int applicationConfig;

    private ApplicationConfig(){
    }

    public static ApplicationConfig getInstance(){
        return INSTANCE;
    }

    public int getConfig(){
        return applicationConfig;
    }

    public void registerConfig(ApplicationSetting applicationSetting) {
        applicationConfig |= applicationSetting.getBit();
        if(applicationSetting.isCors()) {
            Cors cors = Cors.getInstance();
            cors.registerCorsValue(applicationSetting, "*");
        }
    }
}
