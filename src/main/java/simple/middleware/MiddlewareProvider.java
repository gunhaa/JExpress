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
        JExpressExtension JExpressExtension = getMiddleware(applicationSetting);
        JExpressExtension.run();
    }

    private JExpressExtension getMiddleware(ApplicationSetting applicationSetting){
        JExpressExtension JExpressExtension = null;
        switch (applicationSetting){
            case API_DOCS -> JExpressExtension = ApiDocs.getInstance();
            case CORS -> JExpressExtension = Cors.getInstance();
            case RESPONSE_TIME -> JExpressExtension = ResponseTime.getInstance();
            case DB_H2 -> JExpressExtension =H2Database.getInstance();
        }

        return JExpressExtension;
    }
}
