package simple.response;

import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.SimpleHttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static simple.constant.ApplicationSetting.*;

public class HttpResponse {

    private final SimpleHttpRequest simpleHttpRequest;
    private final PrintWriter pw;

    public HttpResponse(SimpleHttpRequest simpleHttpRequest, PrintWriter pw) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.pw = pw;
    }

    /**
     * send default response
     */
    public void send(Object responseBody){
        ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, responseBody);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
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

        ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, htmlContent);

        responseBuilder = responseBuilder.getStaticHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getStaticResponse();
        pw.print(response);
    }

    public void sendError(){

        Optional<ErrorStatus> optionalErrorStatus = Optional.ofNullable(simpleHttpRequest.getErrorQueue().peek());
        ErrorStatus errorStatus = optionalErrorStatus.orElse(ErrorStatus.getDefaultErrorStatus());

        ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, errorStatus);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);

    }
}
