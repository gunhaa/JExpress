package simple.config;

import simple.constant.ApplicationSetting;

import java.util.HashMap;
import java.util.Map;

public class ApplicationConfig {

    private static final ApplicationConfig INSTANCE = new ApplicationConfig();

    private int applicationConfig;
    private final Map<Integer, String> configMap;

    private ApplicationConfig(){
        this.configMap = new HashMap<>();
    }

    public static ApplicationConfig getInstance(){
        return INSTANCE;
    }

    public int getConfig(){
        return INSTANCE.getApplicationConfig();
    }

    //reflection을 이용해 설정값 읽어들인 후 잠궈서 생성하는 방식으로 추가 리팩토링 필요
    public void registerConfig(ApplicationSetting applicationSetting) {
        applicationConfig |= applicationSetting.getBit();
    }

    public void registerConfig(ApplicationSetting applicationSetting, String option){
        applicationConfig |= applicationSetting.getBit();
        configMap.put(applicationSetting.getBit(), option);
    }

    public String getCors(){
        return configMap.get(ApplicationSetting.CORS.getBit());
    }

    private int getApplicationConfig() {
        return applicationConfig;
    }

}
