package simple.httpResponse;

import simple.constant.ServerSettingChecker;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

        pw.print(response);
        pw.flush();
    }

    public void sendFile(String path, OutputStream outputStream) {

        File file = new File(path);

        try  {
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest);

            responseBuilder = responseBuilder.getFileHeader(file.length());
            if(ServerSettingChecker.isServerEnabled(CORS)){
                responseBuilder.cors();
            }
            responseBuilder.crlf();

            String fileHeader = responseBuilder.getFileHeader();

            outputStream.write(fileHeader.getBytes(StandardCharsets.UTF_8));
            outputStream.write(fileBytes);
            outputStream.flush();

        } catch (IOException e) {
            System.err.println("sendFile err");
        }
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
        pw.flush();
    }

    public void sendError(ErrorStatus error){

        ResponseBuilder responseBuilder = new ResponseBuilder(httpRequest, error);

        responseBuilder = responseBuilder.getDefaultHeader();

        if(ServerSettingChecker.isServerEnabled(CORS)){
            responseBuilder.cors();
        }

        StringBuilder response = responseBuilder.getResponse();
        pw.print(response);
        pw.flush();
    }
}
