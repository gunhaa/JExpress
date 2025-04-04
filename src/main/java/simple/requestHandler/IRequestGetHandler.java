package simple.requestHandler;

import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.LambdaHttpRequest;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.HttpResponse;
import simple.httpResponse.LambdaHttpResponse;
import simple.httpResponse.ILambdaHandlerWrapper;

import java.io.OutputStream;
import java.io.PrintWriter;

public class IRequestGetHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new IRequestGetHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private IRequestGetHandler() {}

    @Override
    public void sendResponse(OutputStream outputStream, ILambdaHandlerWrapper ILambdaHandlerWrapper, HttpRequest httpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            // 등록되지 않은 요청
            if(ILambdaHandlerWrapper == null){
                HttpResponse httpResponse = new HttpResponse(httpRequest, pw);
                ErrorStatus errorStatus = new ErrorStatus(HttpStatus.NOT_FOUND_404, "Not valid URL");
                httpResponse.sendError(errorStatus);
                return;
            }

            LambdaHttpRequest lambdaHttpRequest = new LambdaHttpRequest(httpRequest);
            LambdaHttpResponse lambdaHttpResponse = new LambdaHttpResponse(lambdaHttpRequest, pw);
            ILambdaHandlerWrapper.execute(lambdaHttpRequest, lambdaHttpResponse);

        }
    }
}