package simple.middleware;

public class ApiDocs implements Middleware{

    private static volatile ApiDocs INSTANCE;

    private ApiDocs() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static ApiDocs getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiDocs.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiDocs();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        System.out.println("ApiDocs 미들웨어 실행.. 메소드 작성");
    }

}
