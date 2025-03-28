package simple.context;

import simple.constant.ApplicationSetting;

public class ApplicationConfig {

    private static final ApplicationConfig INSTANCE = new ApplicationConfig();

    private int applicationConfig;

    private ApplicationConfig(){}

    public static ApplicationConfig getInstance(){
        return INSTANCE;
    }

    // set이 열려있어 햇갈릴만해서, reflection을 이용해 설정값 읽어들인 후 잠궈서 생성하는 방식으로 추가 리팩토링 필요
    public void setConfig(ApplicationSetting applicationSetting) {
        applicationConfig |= applicationSetting.getBit();
    }

    public int getApplicationConfig() {
        return applicationConfig;
    }

}
