package simple.logger;


import simple.constant.ServerSettingChecker;

import static simple.constant.ApplicationSettingFlags.RESPONSE_TIME;

public class RequestLogger implements ILogger {

    private final StringBuilder log;
    private Long startTime;

    public RequestLogger() {
        this.log = new StringBuilder();
    }

    @Override
    public void add(String message) {
        log.append(message).append("\n");
    }

    @Override
    public void add(char message) {
        log.append(message);
    }

    @Override
    public void print() {
        System.out.println("=============Request log===============");
        System.out.println(log);
    }

}
