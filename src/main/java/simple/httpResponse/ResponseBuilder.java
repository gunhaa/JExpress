package simple.httpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import simple.constant.CustomHttpMethod;
import simple.constant.HttpStatus;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.HttpRequest;
import simple.middleware.Cors;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ResponseBuilder {
    private final StringBuilder sb = new StringBuilder();
    private final HttpRequest httpRequest;
    private final ErrorStatus errorStatus;
    private final ObjectMapper objectMapper = new ObjectMapper();;
    private String entityJson;
    private String entity;


    // 불변 필드
    private static final String CRLF = "\r\n";
    private static final String FIELD_DATE = "Date: ";
    private static final String FIELD_SERVER = "Server: ";
    private static final String FIELD_CONTENT_TYPE = "Content-Type: ";
    private static final String FIELD_CONTENT_LENGTH = "Content-Length: ";
    private static final String FIELD_CONNECTION = "Connection: ";
    private static final String FIELD_CORS = "Access-Control-Allow-Origin: ";

    private static final String VALUE_CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String VALUE_CONNECTION_CLOSED = "close";
    private static final String VALUE_FAVICON = "image/x-icon";

    private static final String DEFAULT_SERVER_NAME = "SimpREST/1.0";

    public ResponseBuilder(HttpRequest httpRequest){
        this.httpRequest = httpRequest;
        this.errorStatus = null;
    }

    public ResponseBuilder(HttpRequest httpRequest, Object entity) {
        this.httpRequest = httpRequest;
        try {
            this.entityJson = objectMapper.writeValueAsString(entity);
        }catch(JsonProcessingException e){
            System.err.println("json parsing error : " + e);
        }
        if (entity instanceof ErrorStatus) {
            this.errorStatus = (ErrorStatus) entity;
        } else {
            this.errorStatus = null;
        }
    }

    public ResponseBuilder cors(){
        String corsValue = Cors.getInstance().getCors();
        sb.append(FIELD_CORS).append(corsValue).append(CRLF);
        return this;
    }

    public ResponseBuilder protocol(){
        String protocol = httpRequest.getProtocol();
        sb.append(protocol).append(" ");
        return this;
    }

    public ResponseBuilder httpStatus(){

        if(errorStatus == null){
            CustomHttpMethod method = httpRequest.getMethod();
            HttpStatus httpStatus = method.getDefaultHttpStatus();
            sb.append(httpStatus.getStatusCode()).append(" ").append(httpStatus.getMessage()).append(CRLF);
            return this;
        } else {
            int statusCode = errorStatus.getHttpStatus().getStatusCode();
            String message = errorStatus.getHttpStatus().getMessage();
            sb.append(statusCode).append(" ").append(message).append(CRLF);
            return this;
        }

    }

    public ResponseBuilder date(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        sb.append(FIELD_DATE).append(dateFormat.format(new Date())).append(CRLF);
        return this;
    }

    public ResponseBuilder server(){
        sb.append(FIELD_SERVER).append(DEFAULT_SERVER_NAME).append(CRLF);
        return this;
    }
    /*
    Connection: close\r\n
    */
    public ResponseBuilder contentType(){
        sb.append(FIELD_CONTENT_TYPE).append(VALUE_CONTENT_TYPE).append(CRLF);
        return this;
    }

    public ResponseBuilder fileContentType(){
        sb.append(FIELD_CONTENT_TYPE).append(VALUE_FAVICON).append(CRLF);
        return this;
    }

    public ResponseBuilder contentLength(){
        sb.append(FIELD_CONTENT_LENGTH).append(entityJson.getBytes(StandardCharsets.UTF_8).length).append(CRLF);
        return this;
    }

    public ResponseBuilder contentCustomLength(Long customLength){
        sb.append(FIELD_CONTENT_LENGTH).append(customLength).append(CRLF);
        return this;
    }

    public ResponseBuilder contentStaticLength(){
//        this.entity = gson.fromJson(this.entityJson, String.class);
        try{
            this.entity = objectMapper.readValue(this.entityJson, String.class);
        }catch (JsonProcessingException e){
            System.err.println("json parsing error : " + e);
        }
        sb.append(FIELD_CONTENT_LENGTH).append(this.entity.getBytes(StandardCharsets.UTF_8).length).append(CRLF);
        return this;
    }

    public ResponseBuilder connection(){
        sb.append(FIELD_CONNECTION).append(VALUE_CONNECTION_CLOSED).append(CRLF);
        return this;
    }

    public ResponseBuilder crlf(){
        sb.append(CRLF);
        return this;
    }

    public ResponseBuilder jsonBody(){

        if(errorStatus == null){
            sb.append(entityJson);
            return this;
        } else {
//            sb.append(gson.toJson(errorStatus));
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                sb.append(objectMapper.writeValueAsString(errorStatus));
            } catch (JsonProcessingException e) {
                System.err.println("json parsing error : " + e);
            }
            return this;
        }

    }

    public ResponseBuilder exceptErrorBody(HttpStatus error){
//        sb.append(gson.toJson(error));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            sb.append(objectMapper.writeValueAsString(error));
        } catch (JsonProcessingException e) {
            System.err.println("json parsing error : " + e);
        }
        return this;
    }

    public ResponseBuilder body(){
        sb.append(this.entity);
        return this;
    }

    public ResponseBuilder getDefaultHeader(){
        return this.protocol()
                .httpStatus()
                .date()
                .contentType()
                .contentLength()
                .server()
                .connection();
    }

    public String getFileHeader(){
        return sb.toString();
    }

    public StringBuilder getResponse() {
        this.crlf().jsonBody();
        return sb;
    }

    public ResponseBuilder getStaticHeader(){
        return this.protocol()
                .httpStatus()
                .date()
                .contentType()
                .contentStaticLength()
                .server()
                .connection();
    }

    public ResponseBuilder getFileHeader(Long customLength){
        return this.protocol()
                .httpStatus()
                .date()
                .fileContentType()
                .contentCustomLength(customLength)
                .server()
                .connection();
    }

    public StringBuilder getStaticResponse(){
        this.crlf().body();
        return sb;
    }

    @Deprecated
    public StringBuilder getErrorResponse(HttpStatus error){
         this.protocol()
                .httpStatus()
                .date()
                .contentType()
                .contentLength()
                .server()
                .connection()
                .crlf()
                .exceptErrorBody(error);
        return sb;
    }
}
