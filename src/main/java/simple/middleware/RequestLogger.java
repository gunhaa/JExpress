package simple.middleware;

public class RequestLogger implements IMiddleWare{

    private static volatile RequestLogger INSTANCE;

    private RequestLogger() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static RequestLogger getInstance() {
        if (INSTANCE == null) {
            synchronized (RequestLogger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RequestLogger();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        System.out.println("Request Logger 실행");
    }
}
