package simple.context;

import simple.constant.ApplicationSetting;

import java.util.HashMap;

public class ApplicationContext {

    private static final ApplicationContext instance = new ApplicationContext();
    private final HashMap<ApplicationSetting, Boolean> applicationContext = new HashMap<>();

    private ApplicationContext(){
        for (ApplicationSetting config : ApplicationSetting.values()) {
            applicationContext.put(config, false);
        }
    }

    public static ApplicationContext getInstance(){
        return instance;
    }

    public void setContext(ApplicationSetting applicationSetting, boolean bool){
        applicationContext.put(applicationSetting, bool);
    }
}
