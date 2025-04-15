package simple.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerManager {
    private final ILogger requestLogger;
    private final ILogger responseTimeLogger;
    private BufferedWriter bufferedWriter;

    private static final String filePath = "logs/log.txt";

    public LoggerManager(LoggerFactory factory) {
        this.requestLogger = factory.createRequestLogger();
        this.responseTimeLogger = factory.createResponseTimeLogger();
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
        } catch(IOException e) {
            System.err.println("bufferedWriter Exception : " + e);
        }
    }

    public void addLog(StringBuilder line){
        requestLogger.add(line.toString());
    }

    public void exportLog() {
        requestLogger.exportLog(bufferedWriter);
        responseTimeLogger.exportLog(bufferedWriter);
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("export log io err");
        }

    }
}
