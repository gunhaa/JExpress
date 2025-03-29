package simple.middleware;

import simple.config.ApplicationConfig;
import simple.constant.ApplicationSetting;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.ApplicationSetting.CORS;

public class Cors implements Middleware{

    private static volatile Cors INSTANCE;
    private final Map<Integer, String> corsMap;

    private Cors() {
        this.corsMap = new HashMap<>();
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static Cors getInstance() {
        if (INSTANCE == null) {
            synchronized (Cors.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Cors();
                }
            }
        }
        return INSTANCE;
    }

    public void registerCorsValue(ApplicationSetting applicationSetting, String option){
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();

        applicationConfig.registerConfig(applicationSetting);
        int config = applicationConfig.getConfig();

        if(validConfig(config, applicationSetting)){
            corsMap.put(applicationSetting.getBit(), option);
//            System.out.println("config는 : " + config);
//            System.out.println("cors 잘 추가됨, 비트는 " + applicationSetting.getBit() );
//            System.out.println("cors 잘 추가됨, option은 " + option );
        } else {
            System.err.println("cors 관련 오류 발생");
//            System.err.println("config는 : " + config);
//            System.err.println("cors 오류, 비트는 " + applicationSetting.getBit() );
//            System.err.println("cors 오류, option은 " + option );
        }
    }

    private boolean validConfig(int config, ApplicationSetting applicationSetting) {
        return applicationSetting.isEnabled(config);
    }

    public String getCors(){
        return corsMap.get(CORS.getBit());
    }

    @Override
    public void run() {
        System.out.println("Cors 미들웨어 실행.. 메소드 작성");
    }
}
