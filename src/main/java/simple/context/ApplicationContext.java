package simple.context;

import simple.constant.ApplicationSetting;

import java.util.HashMap;

import static simple.constant.ApplicationSetting.API_DOCS;

public class ApplicationContext {

    private static final ApplicationContext instance = new ApplicationContext();
    private final HashMap<ApplicationSetting, Boolean> applicationContext = new HashMap<>();

    private ApplicationContext(){
        applicationContext.put(API_DOCS, false);
    }

    public ApplicationContext getInstance(){
        return instance;
    }

    public void useApiDocs(ApplicationSetting applicationSetting, boolean bool){
        applicationContext.put(applicationSetting, bool);
    }
}
