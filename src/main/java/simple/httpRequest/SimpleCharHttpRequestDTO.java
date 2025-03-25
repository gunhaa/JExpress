package simple.httpRequest;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import simple.constant.HttpMethod;
import simple.constant.HttpStatus;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Getter
@Setter
public class SimpleCharHttpRequestDTO {
    private HttpMethod method;
    private String url;
    private String protocol;
    private final HashMap<String, String> queryString = new HashMap<>();
    private final HashMap<String, String> header = new HashMap<>();
    private StringBuilder body = new StringBuilder();
    private LinkedTreeMap<String, Object> bodyMap = new LinkedTreeMap<>();
    private boolean requestLineParsed;
    private boolean parsingHeaders;
    private boolean parsingBody;
    private int contentLength;
    private Queue<simple.httpRequest.ErrorStatus> errorQueue;

    public SimpleCharHttpRequestDTO() {
        this.requestLineParsed = true;
        this.parsingHeaders = false;
        this.parsingBody = false;
        this.contentLength = -1;
        this.errorQueue = new ArrayDeque<>();
    }

    public void addHeader(String line) {
        String[] splitLine = line.split(": ");
        this.header.put(splitLine[0], splitLine[1]);
    }

    public void addRequestLine(String line) {
        String[] request = line.split(" ");
        try {
            this.method = HttpMethod.valueOf(request[0]);
        } catch (IllegalArgumentException e) {
            // error Logic
            System.err.println("Invalid Method Error");
            errorQueue.add(new simple.httpRequest.ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid Method Error"));
        }

        String[] url = request[1].split("\\?");
        if (url.length == 1) {
            this.url = url[0];
        } else {
            this.url = url[0];
            for (int i = 1; i < url.length; i++) {
                String[] splitQueryString = url[i].split("=");
                this.queryString.put(splitQueryString[0], splitQueryString[1]);
            }
        }

        this.protocol = request[2];
        this.requestLineParsed = false;
        this.parsingHeaders = true;
    }

    public void updateRemainingBodyLength(){
        this.parsingHeaders = false;
        this.parsingBody = true;

        String contentLength = this.getHeader().get("Content-Length");
        if (contentLength != null) {
            try {
                this.contentLength = Integer.parseInt(contentLength);
            } catch (NumberFormatException e) {
                // error Logic
                System.err.println("Content-Length Error");
                errorQueue.add(new simple.httpRequest.ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid Content-Length Error"));
            }
        } else {
            this.contentLength = 0;
        }
    }

    public void addRequestBody(String line) {
        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        this.contentLength -= length;
        this.body.append(line);
    }

    public void addRequestBody(char c) {
        byte[] bytes = Character.toString(c).getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        this.contentLength -= length;
        this.body.append(c);
    }

    public void parsingJsonToMap(StringBuilder json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        if(isValidJson(json)){
            this.bodyMap = gson.fromJson(json.toString(), type);
        } else {
            System.err.println("Invalid json body Error");
            errorQueue.add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid json body Error"));
        }
    }

    private boolean isValidJson(StringBuilder json) {
        try {
            JsonParser.parseString(json.toString());
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}