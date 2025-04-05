package simple.httpRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import simple.constant.CustomHttpMethod;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SimpleLineHttpRequestDTO {
    private CustomHttpMethod method;
    private String url;
    private String protocol;
    private final HashMap<String, String> queryString = new HashMap<>();
    private final HashMap<String, String> header = new HashMap<>();
    private StringBuilder body = new StringBuilder();
    private Map<String, Object> bodyMap = new HashMap<>();
    private int contentLength;
    private boolean requestLineParsed;
    private boolean parsingHeaders;
    private boolean parsingBody;

    public SimpleLineHttpRequestDTO() {
        this.requestLineParsed = true;
        this.parsingHeaders = false;
        this.parsingBody = false;
    }

    public void addHeader(String line) {
        String contentLength = this.getHeader().get("Content-Length");
        if (line.isEmpty() && contentLength != null){
            this.parsingHeaders = false;
            this.parsingBody = true;
            this.contentLength = Integer.parseInt(contentLength);
            return;
        }
        String[] splitLine = line.split(": ");
        this.header.put(splitLine[0], splitLine[1]);
    }

    public boolean isFinishBodyParsing(){
        return this.contentLength==0;
    }

    public void requestLineParsingEnd() {
        this.requestLineParsed = false;
        this.parsingHeaders = true;
    }

    public void parsingRequestLine(String line) {
        String[] request = line.split(" ");
        try {
            this.method = CustomHttpMethod.valueOf(request[0]);
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

    
    /**
      해당 방법은 readLine()이r \n \r\n 을 제외하고 읽어오기 때문에 Content-Length를 이용해 정확히 읽을 수 없다
    */
    public void addRequestBody(String line) {
        int length = line.length();
        this.contentLength -= length;
        this.body.append(line);
    }

    public void parsingJsonToMap(StringBuilder json) {
        if(!json.toString().isEmpty()){
//            Gson gson = new Gson();
//            Type type = new TypeToken<Map<String, Object>>() {}.getType();
//            this.bodyMap = gson.fromJson(json.toString(), type);
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                this.bodyMap = objectMapper.readValue(json.toString(), new TypeReference<Map<String, Object>>() {});
            }catch(Exception e){
                System.err.println("Invalid json body Error");
            }
        }
    }
}
