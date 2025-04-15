package simple.logger;

import java.io.BufferedWriter;

public class EmptyLogger implements ILogger{
    @Override
    public void add(String message) {

    }

    @Override
    public void add(char message) {

    }

    @Override
    public void exportLog(BufferedWriter bufferedWriter) {

    }
}
