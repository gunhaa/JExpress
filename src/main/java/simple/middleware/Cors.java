package simple.middleware;

import simple.constant.ApplicationSettingFlags;
import simple.constant.ServerSettingChecker;

import java.util.HashMap;
import java.util.Map;

import static simple.constant.ApplicationSettingFlags.CORS;

public class Cors implements IMiddleWare {

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

    public void registerCorsValue(ApplicationSettingFlags applicationSettingFlags, String option){
        if(ServerSettingChecker.isServerEnabled(applicationSettingFlags)){
            corsMap.put(applicationSettingFlags.getBit(), option);
        } else {
            System.err.println("cors 관련 오류 발생");
        }
    }

    public String getCors(){
        return corsMap.get(CORS.getBit());
    }

    @Override
    public void run() {
        System.out.println("Cors 미들웨어 실행..");
    }
}
