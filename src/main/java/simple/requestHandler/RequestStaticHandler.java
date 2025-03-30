package simple.requestHandler;

import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.SimpleHttpRequest;
import simple.response.HttpResponse;
import simple.response.ResponseHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

import static simple.provider.RequestHandlerProvider.*;

public class RequestStaticHandler implements RequestHandler{

    private static final RequestHandler INSTANCE = new RequestStaticHandler();
    private static final String API_DOCS_HTML = "src/main/resources/static/API/API_DOCS.html";
    private static final String API_DOCS_JS = "src/main/resources/static/API/Jexpress.js";


    public static RequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestStaticHandler() {}

    @Override
    public void sendResponse(OutputStream outputStream, ResponseHandler responseHandler, SimpleHttpRequest simpleHttpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

//            HttpRequest httpRequest = new HttpRequest(simpleHttpRequest);
            HttpResponse httpResponse = new HttpResponse(simpleHttpRequest, pw);

            if(simpleHttpRequest.getUrl().endsWith(URL_HTML)){
                httpResponse.sendStatic(API_DOCS_HTML);
                return;
            }

            if(simpleHttpRequest.getUrl().endsWith(URL_JAVASCRIPT)){
                httpResponse.sendStatic(API_DOCS_JS);
                return;
            }

            if(simpleHttpRequest.getUrl().equals(URL_FAVICON)){
                ErrorStatus errorStatus = new ErrorStatus(HttpStatus.NOT_FOUND_404, "favicon not found");
                httpResponse.sendError(errorStatus);
                return;
            }
        }
    }
}
