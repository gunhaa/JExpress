package simple.tempEntity;

import com.google.gson.Gson;

import java.io.PrintWriter;

public class ResponseSuccess {

    private int statusCode;
    private Object entity;
    private Gson gson = new Gson();

    public ResponseSuccess(int statusCode, Object entity) {
        this.statusCode = statusCode;
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
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
