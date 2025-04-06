package simple.requestHandler;

import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;
import simple.httpRequest.LambdaHttpRequest;
import simple.httpResponse.HttpResponse;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHttpResponse;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestPostHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new RequestPostHandler();

    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestPostHandler() {
    }

    @Override
    public void sendResponse(OutputStream outputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            // 등록되지 않은 요청
            if(ILambdaHandler == null){
                HttpResponse httpResponse = new HttpResponse(httpRequest, pw);
                ErrorStatus errorStatus = new ErrorStatus(HttpStatus.NOT_FOUND_404, "Not valid URL");
                httpResponse.sendError(errorStatus);
                return;
            }

            LambdaHttpRequest lambdaHttpRequest = new LambdaHttpRequest(httpRequest);
            LambdaHttpResponse lambdaHttpResponse = new LambdaHttpResponse(lambdaHttpRequest, pw);
            ILambdaHandler.execute(lambdaHttpRequest, lambdaHttpResponse);

        }
    }
}
