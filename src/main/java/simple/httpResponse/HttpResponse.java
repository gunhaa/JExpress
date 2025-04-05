package simple.httpResponse;

import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static simple.constant.ApplicationSettingFlags.CORS;

public class HttpResponse {


    private final HttpRequest httpRequest;
    private final PrintWriter pw;

    public HttpResponse(HttpRequest httpRequest, PrintWriter pw) {
        this.httpRequest = httpRequest;
        this.pw = pw;
    }

    /**
     * send static api-docs response
     */
    public void sendStatic(String path){

        File apiDocs = new File(path);

        byte[] fileContent = new byte[(int) apiDocs.length()];

        try (FileInputStream fis = new FileInputStream(apiDocs)) {
            fis.read(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String htmlContent = new String(fileContent, StandardCharsets.UTF_8);

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, htmlContent);

        responseBuilder = responseBuilder.getStaticHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getStaticResponse();
//        responseBuilder.printTestSb();

        pw.print(response);
    }

    public void sendError(){

        Optional<ErrorStatus> optionalErrorStatus = Optional.ofNullable(httpRequest.getErrorQueue().peek());
        ErrorStatus errorStatus = optionalErrorStatus.orElse(ErrorStatus.getDefaultErrorStatus());

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, errorStatus);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);

    }

    public void sendError(ErrorStatus error){

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, error);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
    }
}
