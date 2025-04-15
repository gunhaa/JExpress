package simple.logger;


import simple.constant.ServerSettingChecker;

import java.io.BufferedWriter;
import java.io.IOException;

import static simple.constant.ApplicationSettingFlags.RESPONSE_TIME;

public class RequestLogger implements ILogger {

    private final StringBuilder log;

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
    public void exportLog(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.newLine();
            bufferedWriter.write(this.log.toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.err.println("log io err");
        }
    }

}
