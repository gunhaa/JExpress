package simple.logger;


public class SingleThreadRuntimeLogger implements Logger{

    private final StringBuilder log = new StringBuilder();


    @Override
    public void add(String message) {
        log.append(message);
    }

    @Override
    public void add(char message) {
        log.append(message);
    }

    @Override
    public void print() {
        System.out.println("log : " + log);
    }

}
