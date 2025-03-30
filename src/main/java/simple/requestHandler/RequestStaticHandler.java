package simple.requestHandler;

import simple.httpRequest.SimpleHttpRequest;
import simple.response.HttpResponse;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RequestStaticHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestStaticHandler();
    private static final String PATH = "src/main/resources/static/API/API_DOCS.html";


    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestStaticHandler() {}

    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            HttpResponse lambdaHttpResponse = new HttpResponse(simpleHttpRequest, pw);
            lambdaHttpResponse.sendApiDocs(PATH);

        }
    }
}
