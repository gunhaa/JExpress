package simple.config;

import simple.constant.ApplicationSettingFlags;
import simple.middleware.Cors;

import static simple.constant.ApplicationSettingFlags.CORS;

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
        if(applicationSettingFlags == CORS){
            Cors cors = Cors.getInstance();
            cors.registerCorsValue(applicationSettingFlags, "*");
        }
    }

    public void unRegisterConfig(ApplicationSettingFlags applicationSettingFlags){
        applicationConfig &= ~applicationSettingFlags.getBit();
    }

    // for test
    public void clearConfigs(){
        this.applicationConfig = 0;
    }
}
