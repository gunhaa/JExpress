package simple.httpRequest;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import simple.constant.HttpMethod;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SimpleLineHttpRequestDTO {
    private HttpMethod method;
    private String url;
    private String protocol;
    private final HashMap<String, String> queryString = new HashMap<>();
    private final HashMap<String, String> header = new HashMap<>();
    private StringBuilder body = new StringBuilder();
    private LinkedTreeMap<String, Object> bodyMap = new LinkedTreeMap<>();
    private boolean requestLineParsed;
    private boolean parsingHeaders;
    private boolean hasBody;
    private boolean parsingBodyFinish;

    public SimpleLineHttpRequestDTO() {
        this.requestLineParsed = true;
        this.parsingHeaders = false;
        this.hasBody = false;
        this.parsingBodyFinish = false;
    }

    public void addHeader(String line) {
        if (line.isEmpty()){
            this.parsingHeaders = false;
            this.hasBody = true;
            return;
        }
        String[] splitLine = line.split(": ");
        this.header.put(splitLine[0], splitLine[1]);
    }

    public void requestLineParsingEnd() {
        this.requestLineParsed = false;
        this.parsingHeaders = true;
    }

    public void parsingRequestLine(String line) {
        String[] request = line.split(" ");
        try {
            this.method = HttpMethod.valueOf(request[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 요청입니다");
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
        this.requestLineParsingEnd();
    }

    public void addRequestBody(String line) {
        this.body.append(line);
    }

    public void parsingJsonToMap(StringBuilder json) {
        if(!json.toString().isEmpty()){
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            this.bodyMap = gson.fromJson(json.toString(), type);
            this.parsingBodyFinish = true;
        }
    }
}
