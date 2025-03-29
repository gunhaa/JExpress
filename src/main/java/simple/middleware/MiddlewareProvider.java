package simple.middleware;

import simple.constant.ApplicationSetting;

public class MiddlewareProvider {

    private static volatile MiddlewareProvider INSTANCE;

    private MiddlewareProvider() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static MiddlewareProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (MiddlewareProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MiddlewareProvider();
                }
            }
        }
        return INSTANCE;
    }

    public void execute(ApplicationSetting applicationSetting){
        Middleware middleware = getMiddleware(applicationSetting);
        middleware.run();
    }

    private Middleware getMiddleware(ApplicationSetting applicationSetting){
        Middleware middleware = null;
        switch (applicationSetting){
            case API_DOCS -> middleware = ApiDocs.getInstance();
            case CORS -> middleware = Cors.getInstance();
            case RESPONSE_TIME -> middleware = ResponseTime.getInstance();
        }

        return middleware;
    }
}
