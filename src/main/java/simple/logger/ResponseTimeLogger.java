package simple.logger;

public class ResponseTimeLogger implements ILogger{

    private Long startTime;

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
    public void print() {
        if(startTime != null){
            Long responseTime = System.currentTimeMillis();
            System.out.println("response time : " + (responseTime- this.startTime) + "ms");
        }
    }
}
