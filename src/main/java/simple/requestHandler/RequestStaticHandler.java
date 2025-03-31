package simple.requestHandler;

import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;
import simple.response.HttpResponse;
import simple.response.LambdaHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

import static simple.constant.HttpStatus.NOT_FOUND_404;
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
    public void sendResponse(OutputStream outputStream, LambdaHandler lambdaHandler, HttpRequest httpRequest) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

//            HttpRequest httpRequest = new HttpRequest(simpleHttpRequest);
            HttpResponse httpResponse = new HttpResponse(httpRequest, pw);

            if(httpRequest.getUrl().equals("/api-docs.html")){
                httpResponse.sendStatic(API_DOCS_HTML);
                return;
            }

            if(httpRequest.getUrl().equals("/Jexpress.js")){
                httpResponse.sendStatic(API_DOCS_JS);
                return;
            }

            if(httpRequest.getUrl().equals(URL_FAVICON)){
                ErrorStatus faviconNotFound = new ErrorStatus(NOT_FOUND_404, "favicon not found");
                httpResponse.sendError(faviconNotFound);
                return;
            }

            ErrorStatus pageNotFound = new ErrorStatus(NOT_FOUND_404, "Page Not Found");
            httpResponse.sendError(pageNotFound);
        }
    }
}
