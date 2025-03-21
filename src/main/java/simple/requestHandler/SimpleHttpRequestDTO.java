package simple.requestHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;

@Builder
@Getter
@Setter
public class SimpleHttpRequestDTO {
    private String requestLine;
    private final HashMap<String, String> header = new HashMap<>();
    private String jsonBody;
    private boolean isRequestLine = true;
    private boolean isHeader = true;
    private boolean isRequestBody = false;

    public SimpleHttpRequestDTO() {
    }

    public void addHeader(String line) {
        String[] splitLine = line.split(": ");
        System.out.println("test add header : " + Arrays.toString(splitLine));
        this.header.put(splitLine[0], splitLine[1]);
    }

    public boolean isRequestBody () {
        return isRequestBody;
    }

    public boolean isRequestLine() {
        return isRequestLine;
    }

    public boolean isHeader() {
        return isHeader;
    }

}
