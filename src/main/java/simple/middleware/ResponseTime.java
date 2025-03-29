package simple.middleware;

import java.util.HashMap;
import java.util.Map;

public class ResponseTime implements Middleware{

    private static volatile ResponseTime INSTANCE;

    private ResponseTime() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static ResponseTime getInstance() {
        if (INSTANCE == null) {
            synchronized (ResponseTime.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ResponseTime();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public void run() {
        System.out.println("ResponseTime 실행");
    }
}
