package simple.httpRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@Setter
public class HttpRequestDTO {
    private String requestLine;
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


    public void addRequestBody(String line){
        this.body.append(line);
    }

}
