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
        int config = applicationConfig.getConfig();

        if(validConfig(config, applicationSetting)){
            corsMap.put(applicationSetting.getBit(), option);
        } else {
            System.err.println("cors 관련 오류 발생");
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
