package simple.constant;

import simple.config.ApplicationConfig;

public class ServerSettingChecker {

    private static final ApplicationConfig applicationConfig = ApplicationConfig.getInstance();

    private static final ServerSettingChecker INSTANCE = new ServerSettingChecker();

    private ServerSettingChecker() {
    }

    public static ServerSettingChecker getInstance() {
        return INSTANCE;
    }

    public static boolean isServerEnabled(ApplicationSetting applicationSetting){
        int config = applicationConfig.getConfig();
        return applicationSetting.isSettingEnabled(config);
    }

}
