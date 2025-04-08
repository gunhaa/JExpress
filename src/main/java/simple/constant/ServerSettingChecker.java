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

    public static boolean isServerEnabled(ApplicationSettingFlags applicationSettingFlags){
        int config = applicationConfig.getConfig();
        return applicationSettingFlags.isSettingEnabled(config);
    }

    public static boolean isH2AndMySQLEnabled(int config){
        return (config & ApplicationSettingFlags.DB_H2.getBit()) != 0
                && (config & ApplicationSettingFlags.DB_MYSQL.getBit()) != 0;
    }

}
