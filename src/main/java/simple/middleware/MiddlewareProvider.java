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
        IMiddleWare IMiddleWare = getMiddleware(applicationSetting);
        IMiddleWare.run();
    }

    private IMiddleWare getMiddleware(ApplicationSetting applicationSetting){
        IMiddleWare IMiddleWare = null;
        switch (applicationSetting){
            case API_DOCS -> IMiddleWare = ApiDocs.getInstance();
            case CORS -> IMiddleWare = Cors.getInstance();
            case RESPONSE_TIME -> IMiddleWare = ResponseTime.getInstance();
            case DB_H2 -> IMiddleWare =H2Database.getInstance();
        }

        return IMiddleWare;
    }
}
