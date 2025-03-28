package simple.context;

import simple.constant.ApplicationSetting;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConfig {

    private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    private final List<ApplicationSetting> applicationContext = new ArrayList<>();

    private ApplicationConfig(){}

    public static ApplicationConfig getInstance(){
        return INSTANCE;
    }

    public void setConfig(ApplicationSetting applicationSetting){
        applicationContext.add(applicationSetting);
    }
}
