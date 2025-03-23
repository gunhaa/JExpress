package simple.response;

import com.google.gson.Gson;
import simple.constant.HttpStatus;
import simple.httpRequest.SimpleHttpRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HttpBuilder {
    private final StringBuilder sb = new StringBuilder();
    private final SimpleHttpRequest simpleHttpRequest;
    private final HttpStatus userCustomHttpStatus;
    private final Object entity;
    private final Gson gson = new Gson();
    private final String entityJson;

    // 불변 필드
    private static final String CRLF = "\r\n";
    private static final String FIELD_DATE = "Date: ";
    private static final String FIELD_SERVER = "Server: ";
    private static final String FIELD_CONTENT_TYPE = "Content-Type: ";
    private static final String FIELD_CONTENT_LENGTH = "Content-Length: ";
    private static final String FIELD_CONNECTION = "Connection: ";

    private static final String VALUE_CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String VALUE_CONNECTION_CLOSED = "close";

    private static final String DEFAULT_SERVER_NAME = "SimpREST/1.0";

    /*
    HTTP/1.1 200 OK\r\n
    Date: Sun, 23 Mar 2025 12:00:00 GMT\r\n
    Server: MyServer/1.0\r\n
    Content-Type: text/html; charset=UTF-8\r\n
    Content-Length: 48\r\n
    Connection: close\r\n
    \r\n
    <html><body><h1>Hello, World!</h1></body></html>
    */

    public HttpBuilder(SimpleHttpRequest simpleHttpRequest, HttpStatus userCustomHttpStatus, Object entity) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.userCustomHttpStatus = userCustomHttpStatus;
        this.entity = entity;
        this.entityJson = gson.toJson(entity);
    }

    public HttpBuilder protocol(){
        String protocol = simpleHttpRequest.getProtocol();
        sb.append(protocol).append(" ");
        return this;
    }

    public HttpBuilder httpStatus(){
        int statusCode = userCustomHttpStatus.getStatusCode();
        String message = userCustomHttpStatus.getMessage();
        sb.append(statusCode).append(" ").append(message).append(CRLF);
        return this;
    }

    public HttpBuilder date(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        sb.append(FIELD_DATE).append(dateFormat.format(new Date())).append(CRLF);
        return this;
    }

    public HttpBuilder server(){
        sb.append(FIELD_SERVER).append(DEFAULT_SERVER_NAME).append(CRLF);
        return this;
    }
    /*
    Connection: close\r\n
    */
    public HttpBuilder contentType(){
        sb.append(FIELD_CONTENT_TYPE).append(VALUE_CONTENT_TYPE).append(CRLF);
        return this;
    }

    public HttpBuilder contentLength(){
        sb.append(FIELD_CONTENT_LENGTH).append(entityJson.length()).append(CRLF);
        return this;
    }

    public HttpBuilder connection(){
        sb.append(FIELD_CONNECTION).append(VALUE_CONNECTION_CLOSED).append(CRLF);
        return this;
    }

    public HttpBuilder crlf(){
        sb.append(CRLF);
        return this;
    }

    public HttpBuilder body(){
        sb.append(entityJson);
        return this;
    }

    public StringBuilder getSb() {
        return sb;
    }
}
