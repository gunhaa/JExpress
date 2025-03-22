package simple.response;

import com.google.gson.Gson;
import lombok.Getter;
import simple.constant.HttpStatus;

import java.io.OutputStream;
import java.io.PrintWriter;

@Getter
public class ResponseSuccess {

    private final HttpStatus httpStatusCode;
    private final Object response;
    private final Gson gson = new Gson();

    public ResponseSuccess(HttpStatus httpStatusCode, Object entity) {
        this.httpStatusCode = httpStatusCode;
        this.response = entity;
    }

    public void basic(OutputStream out){
        PrintWriter pw = new PrintWriter(out);
        String entityJson = gson.toJson(response);
        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-Type: application/json");
        pw.println("Content-Length: " + entityJson.length());
        pw.println();
        pw.println(entityJson);
        pw.flush();
        pw.close();
    }
}
