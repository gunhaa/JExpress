package simple.httpRequest;

import lombok.Builder;
import lombok.Getter;
import simple.constant.CustomHttpMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Builder
@Getter
public class HttpRequest {
    private final CustomHttpMethod method;
    private final String url;
    private final String protocol;
    private final HashMap<String, String> queryString;
    private HashMap<String, String> params;
    private final HashMap<String, String> header;
    private final Map<String, Object> bodyMap;
    private final Queue<ErrorStatus> errorQueue;

    public void setParams(String key, String value){
        this.params.put(key, value);
    }

    public static HttpRequest createMockUrl(String mock){
        return builder()
                .method(CustomHttpMethod.GET)
                .url(mock)
                .protocol("protocol")
                .queryString(new HashMap<>())
                .header(new HashMap<>())
                .bodyMap(new HashMap<>())
                .errorQueue(new LinkedList<>())
                .build();
    }
}
