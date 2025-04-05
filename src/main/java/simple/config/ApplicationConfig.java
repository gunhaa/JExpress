package simple.config;

import simple.constant.ApplicationSettingFlags;
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

    public void registerConfig(ApplicationSettingFlags applicationSettingFlags) {
        applicationConfig |= applicationSettingFlags.getBit();
        if(applicationSettingFlags.isCors()) {
            Cors cors = Cors.getInstance();
            cors.registerCorsValue(applicationSettingFlags, "*");
        }
    }
}
