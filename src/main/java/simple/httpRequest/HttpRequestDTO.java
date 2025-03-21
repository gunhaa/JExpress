package simple.httpRequest;

import lombok.Getter;
import lombok.Setter;
import simple.httpMethod.HttpMethod;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@Setter
public class HttpRequestDTO {
    private HttpMethod method;
    private String url;
    private String protocol;
    private final HashMap<String, String> header = new HashMap<>();
    private StringBuilder body = new StringBuilder();
    private boolean requestLineParsed;
    private boolean parsingHeaders;
    private boolean parsingBody;

    public HttpRequestDTO() {
        this.requestLineParsed = true;
        this.parsingHeaders = false;
        this.parsingBody = false;
    }

    public void addHeader(String line) {
        String[] splitLine = line.split(": ");
        System.out.println("test add header : " + Arrays.toString(splitLine));
        this.header.put(splitLine[0], splitLine[1]);
    }

    public void parsingRequestLine(String line){
        String[] request = line.split(" ");
        try {
            this.method = HttpMethod.valueOf(request[0]);
        } catch (IllegalStateException e){
            System.out.println("Invalid Http Method : " + request[0]);
            System.exit(1);
        }
        this.url = request[1];
        this.protocol = request[2];
    }

    public void addRequestBody(String line){
        this.body.append(line);
    }

}
