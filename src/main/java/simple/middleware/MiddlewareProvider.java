package simple.middleware;

import simple.constant.ApplicationSettingFlags;

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

    public void execute(ApplicationSettingFlags applicationSettingFlags){
        IMiddleWare IMiddleWare = getMiddleware(applicationSettingFlags);
        IMiddleWare.run();
    }

    private IMiddleWare getMiddleware(ApplicationSettingFlags applicationSettingFlags){
        IMiddleWare IMiddleWare = null;
        switch (applicationSettingFlags){
            case API_DOCS -> IMiddleWare = ApiDocs.getInstance();
            case CORS -> IMiddleWare = Cors.getInstance();
            case REQUEST_LOGGER -> IMiddleWare = RequestLogger.getInstance();
            case RESPONSE_TIME -> IMiddleWare = ResponseTime.getInstance();
            case DB_H2 -> IMiddleWare = H2Initialize.getInstance();
            case DB_MYSQL -> IMiddleWare = MySQLInitialize.getInstance();
        }

        return IMiddleWare;
    }
}
