package simple.httpResponse;

public class LambdaHandlerWrapper {

    private final ILambdaHandler ILambdaHandler;
    private Class<?> clazz;

    public LambdaHandlerWrapper(ILambdaHandler ILambdaHandler) {
        this.ILambdaHandler = ILambdaHandler;
    }

    public LambdaHandlerWrapper(ILambdaHandler ILambdaHandler, Class<?> clazz) {
        this.ILambdaHandler = ILambdaHandler;
        this.clazz = clazz;
    }

    public ILambdaHandler unwrap(){
        return ILambdaHandler;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
