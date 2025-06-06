package simple.requestHandler;

import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;
import simple.httpResponse.HttpResponse;
import simple.lambda.ILambdaHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

import static simple.constant.HttpStatus.NOT_FOUND_404;
import static simple.provider.RequestHandlerProvider.URL_FAVICON;

public class RequestStaticHandler implements IRequestHandler {

    private static final IRequestHandler INSTANCE = new RequestStaticHandler();
    private static final String API_DOCS_HTML = "src/main/resources/static/API/API_DOCS.html";
    private static final String API_DOCS_JS = "src/main/resources/static/API/JExpress.js";
    private static final String API_DOCS_FAVICON = "src/main/resources/static/API/favicon.ico";


    public static IRequestHandler getInstance(){
        return INSTANCE;
    }

    private RequestStaticHandler() {}

    @Override
    public void sendResponse(OutputStream outputStream, HttpRequest httpRequest, ILambdaHandler ILambdaHandler) {
        try(PrintWriter pw = new PrintWriter(outputStream, true)){

            HttpResponse httpResponse = new HttpResponse(httpRequest, pw);

            if(httpRequest.getUrl().equals("/api-docs.html")){
                httpResponse.sendStatic(API_DOCS_HTML);
                return;
            }

            if(httpRequest.getUrl().equals("/JExpress.js")){
                httpResponse.sendStatic(API_DOCS_JS);
                return;
            }

            if(httpRequest.getUrl().equals(URL_FAVICON)){
                httpResponse.sendFile(API_DOCS_FAVICON, outputStream);
                return;
            }

            ErrorStatus pageNotFound = new ErrorStatus(NOT_FOUND_404, "Page Not Found");
            httpResponse.sendError(pageNotFound);
        }
    }
}
