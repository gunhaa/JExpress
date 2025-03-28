package simple.context;


import simple.constant.ApplicationSetting;
import simple.middleware.MiddlewareProvider;

import java.util.List;

public class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance(){
        return INSTANCE;
    }

    public static void initializeApplicationContext(){
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        List<ApplicationSetting> settingList = applicationConfig.getApplicationConfig();

        MiddlewareProvider middlewareProvider = MiddlewareProvider.getInstance();
        for (ApplicationSetting applicationSetting : settingList) {

            middlewareProvider.execute(applicationSetting);
            System.out.println("설정된 applicationSetting : " + applicationSetting);

        }
    }
}
