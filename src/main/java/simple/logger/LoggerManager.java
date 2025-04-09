package simple.logger;

public class LoggerManager {
    private final ILogger requestLogger;
    private final ILogger responseTimeLogger;

    public LoggerManager(LoggerFactory factory) {
        this.requestLogger = factory.createRequestLogger();
        this.responseTimeLogger = factory.createResponseTimeLogger();
    }

    public void addLog(StringBuilder line){
        requestLogger.add(line.toString());
    }

    public void printAll() {
        requestLogger.print();
        responseTimeLogger.print();
    }
}
