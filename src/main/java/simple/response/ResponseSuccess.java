package simple.response;

import com.google.gson.Gson;
import lombok.Getter;
import simple.httpMethod.HttpStatus;

import java.io.PrintWriter;

@Getter
public class ResponseSuccess {

    private final HttpStatus httpStatusCode;
    private final Object entity;
    private final Gson gson = new Gson();

    public ResponseSuccess(HttpStatus httpStatusCode, Object entity) {
        this.httpStatusCode = httpStatusCode;
        this.entity = entity;
    }


    public void responseParser(PrintWriter out){
        String entityJson = gson.toJson(entity);
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: application/json");
        out.println("Content-Length: " + entityJson.length());
        out.println();
        out.println(entityJson);
        out.flush();
    }
}
