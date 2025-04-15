package simple.logger;

import java.io.BufferedWriter;

public interface ILogger {
    void add(String message);
    void add(char message);
    void exportLog(BufferedWriter bufferedWriter);
}
