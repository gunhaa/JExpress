package simple.httpRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import simple.constant.CustomHttpMethod;
import simple.constant.HttpStatus;
import simple.decoder.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Getter
@Setter
public class CharHttpRequestBuilder {
    private CustomHttpMethod method;
    private String url;
    private String protocol;
    private final HashMap<String, String> queryString = new HashMap<>();
    private final HashMap<String, String> header = new HashMap<>();
    private StringBuilder body = new StringBuilder();
    private Map<String, String> bodyMap = new HashMap<>();
    private boolean requestLineParsed;
    private boolean parsingHeaders;
    private boolean parsingBody;
    private int contentLength;
    private Queue<ErrorStatus> errorQueue = new ArrayDeque<>();
    private StringBuilder logBuffer = new StringBuilder();

    public CharHttpRequestBuilder() {
        this.requestLineParsed = true;
        this.parsingHeaders = false;
        this.parsingBody = false;
        this.contentLength = -1;
    }

    public void addHeader(String line) {
        String[] splitLine = line.split(": ");
        logBuffer.append(line).append("\r\n");
        this.header.put(splitLine[0], splitLine[1]);
    }

    public void addRequestLine(String line) {
        String[] request = line.split(" ");
        logBuffer.append(line).append("\r\n");
        try {
            this.method = CustomHttpMethod.valueOf(request[0]);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Method Error");
            errorQueue.add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid Method Error"));
        }

        // request[1] = url
        String[] urlSegment = request[1].split("\\?");
        this.url = urlSegment[0];

        if (urlSegment.length > 1) {
            String[] queryParams = urlSegment[1].split("&");

            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String rawValue = keyValue[1];
                    String value = URLDecoder.decodeURL(rawValue);
                    this.queryString.put(keyValue[0], value);
                }
            }
        }

        this.protocol = request[2];
        this.requestLineParsed = false;
        this.parsingHeaders = true;
    }

    public void updateRemainingBodyLength() {
        this.parsingHeaders = false;
        this.parsingBody = true;
        this.logBuffer.append("\r\n");

        String contentLength = this.getHeader().get("Content-Length");
        if (contentLength != null) {
            try {
                this.contentLength = Integer.parseInt(contentLength);
            } catch (NumberFormatException e) {
                System.err.println("Invalid Content-Length Error");
                errorQueue.add(new simple.httpRequest.ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid Content-Length Error"));
            }
        } else {
            this.contentLength = 0;
        }
    }

    public void addRequestBody(String line) {
        logBuffer.append(line);
        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        this.contentLength -= length;
        this.body.append(line);
    }

    public void addRequestBody(char c) {
        logBuffer.append(c);
        byte[] bytes = Character.toString(c).getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        this.contentLength -= length;
        this.body.append(c);
    }

    public void parsingJsonToMap(StringBuilder json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.bodyMap = objectMapper.readValue(json.toString(), new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            System.err.println("Invalid json body Error");
            errorQueue.add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid json body Error"));
        }
    }
}