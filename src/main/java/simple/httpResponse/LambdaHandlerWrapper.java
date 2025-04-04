package simple.httpResponse;

public class LambdaHandlerWrapper {

    private final ILambdaHandlerWrapper ILambdaHandlerWrapper;
    private Class<?> clazz;

    public LambdaHandlerWrapper(ILambdaHandlerWrapper ILambdaHandlerWrapper) {
        this.ILambdaHandlerWrapper = ILambdaHandlerWrapper;
    }

    public LambdaHandlerWrapper(ILambdaHandlerWrapper ILambdaHandlerWrapper, Class<?> clazz) {
        this.ILambdaHandlerWrapper = ILambdaHandlerWrapper;
        this.clazz = clazz;
    }

    public ILambdaHandlerWrapper unwrap(){
        return ILambdaHandlerWrapper;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
