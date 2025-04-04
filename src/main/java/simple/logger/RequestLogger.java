package simple.logger;


import simple.constant.ServerSettingChecker;

import static simple.constant.ApplicationSetting.RESPONSE_TIME;

public class RequestLogger implements ILogger {

    private final StringBuilder log;
    private Long startTime;

    public RequestLogger() {
        this.log = new StringBuilder();
        if(ServerSettingChecker.isServerEnabled(RESPONSE_TIME)){
            this.startTime = System.currentTimeMillis();
        }
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
        System.out.println("===============log===============");
        System.out.println();
        System.out.println(log);
        if(startTime != null){
            Long responseTime = System.currentTimeMillis();
            System.out.println("response time : " + (responseTime- this.startTime) + "ms");
        }
    }

}
