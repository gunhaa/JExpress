package simple.response;

import com.google.gson.Gson;
import simple.constant.HttpMethod;
import simple.constant.HttpStatus;
import simple.config.ApplicationConfig;
import simple.httpRequest.ErrorStatus;
import simple.httpRequest.SimpleHttpRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ResponseBuilder {
    private final StringBuilder sb = new StringBuilder();
    private final SimpleHttpRequest simpleHttpRequest;
    private final ErrorStatus errorStatus;
    private final Gson gson = new Gson();
    private final String entityJson;


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

    private static final String DEFAULT_SERVER_NAME = "SimpREST/1.0";

    public ResponseBuilder(SimpleHttpRequest simpleHttpRequest, Object entity) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.entityJson = gson.toJson(entity);
        this.errorStatus = null;
    }

    public ResponseBuilder(SimpleHttpRequest simpleHttpRequest, Object entity, ErrorStatus errorStatus) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.errorStatus = errorStatus;
        this.entityJson = gson.toJson(entity);
    }

    public ResponseBuilder cors(){
        String cors = ApplicationConfig.getInstance().getCors();
        sb.append(cors).append(" ");
        return this;
    }

    public ResponseBuilder protocol(){
        String protocol = simpleHttpRequest.getProtocol();
        sb.append(protocol).append(" ");
        return this;
    }

    public ResponseBuilder httpStatus(){

        if(errorStatus == null){
            HttpMethod method = simpleHttpRequest.getMethod();
            HttpStatus httpStatus = method.getHttpStatus();
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

    public ResponseBuilder contentLength(){
        sb.append(FIELD_CONTENT_LENGTH).append(entityJson.length()).append(CRLF);
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
            sb.append(errorStatus.getMessage());
            return this;
        }

    }

    public StringBuilder getResponse() {
        return sb;
    }

    public ResponseBuilder getDefaultResponse(){
        return this.protocol()
                .httpStatus()
                .date()
                .contentType()
                .contentLength()
                .server()
                .connection()
                .crlf()
                .jsonBody();
    }
}
