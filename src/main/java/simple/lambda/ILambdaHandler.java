package simple.lambda;

@FunctionalInterface
public interface ILambdaHandler {
    void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse);
}
