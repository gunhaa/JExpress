package simple.logger;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseTimeLogger implements ILogger {

    private final Long startTime;

    public ResponseTimeLogger() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void add(String message) {

    }

    @Override
    public void add(char message) {

    }

    @Override
    public void exportLog(BufferedWriter bufferedWriter) {
        if (startTime != null) {
            Long responseTime = System.currentTimeMillis();
            try {
                bufferedWriter.newLine();
                bufferedWriter.write("ResponseTime : ");
                bufferedWriter.write(Long.toString(responseTime - this.startTime));
                bufferedWriter.write("ms");
                bufferedWriter.newLine();
            } catch (IOException e) {
                System.err.println("response time io err");
            }
        }
    }
}
