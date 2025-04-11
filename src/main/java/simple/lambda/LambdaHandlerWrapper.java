package simple.lambda;

public class LambdaHandlerWrapper {

    private final ILambdaHandler ILambdaHandler;
    private Class<?> clazz;

    public LambdaHandlerWrapper(ILambdaHandler iLambdaHandler) {
        this.ILambdaHandler = iLambdaHandler;
    }

    public LambdaHandlerWrapper(ILambdaHandler iLambdaHandler, Class<?> clazz) {
        this.ILambdaHandler = iLambdaHandler;
        this.clazz = clazz;
    }

    public ILambdaHandler unwrap(){
        return ILambdaHandler;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
