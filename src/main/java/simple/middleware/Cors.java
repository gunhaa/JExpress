package simple.middleware;

import simple.mapper.GetMapper;
import simple.mapper.Mapper;

public class Cors implements Middleware{

    private static volatile Cors INSTANCE;

    private Cors() {
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

    @Override
    public void run() {
        System.out.println("Cors 미들웨어 실행.. 메소드 작성");
    }
}
