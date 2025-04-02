package simple.middleware;

public class ResponseTime implements JExpressExtension {

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
