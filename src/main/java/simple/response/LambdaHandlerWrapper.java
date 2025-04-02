package simple.response;

public class LambdaHandlerWrapper {

    private final LambdaHandler lambdaHandler;
    private Class<?> clazz;

    public LambdaHandlerWrapper(LambdaHandler lambdaHandler) {
        this.lambdaHandler = lambdaHandler;
    }

    public LambdaHandlerWrapper(LambdaHandler lambdaHandler, Class<?> clazz) {
        this.lambdaHandler = lambdaHandler;
        this.clazz = clazz;
    }

    public LambdaHandler getLambdaHandler() {
        return lambdaHandler;
    }
}
