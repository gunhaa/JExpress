package simple.logger;

import simple.constant.ServerSettingChecker;

import static simple.constant.ApplicationSettingFlags.REQUEST_LOGGER;
import static simple.constant.ApplicationSettingFlags.RESPONSE_TIME;

public class LoggerFactory {

    private final boolean isEnableRequestLogging;
    private final boolean isEnableResponseTimeLogging;

    public LoggerFactory() {
        this.isEnableRequestLogging = ServerSettingChecker.isServerEnabled(REQUEST_LOGGER);
        this.isEnableResponseTimeLogging = ServerSettingChecker.isServerEnabled(RESPONSE_TIME);
    }

    public ILogger createRequestLogger(){
        return isEnableRequestLogging ? new RequestLogger() : new EmptyLogger();
    }

    public ILogger createResponseTimeLogger(){
        return isEnableResponseTimeLogging ? new ResponseTimeLogger() : new EmptyLogger();
    }
}
