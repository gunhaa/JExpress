package simple.httpRequest;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Builder;
import lombok.Getter;
import simple.constant.CustomHttpMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Builder
@Getter
public class HttpRequest {
    private final CustomHttpMethod method;
    private final String url;
    private final String protocol;
    private final HashMap<String, String> queryString;
    private HashMap<String, String> param;
    private final HashMap<String, String> header;
    private final LinkedTreeMap<String, Object> bodyMap;
    private final Queue<ErrorStatus> errorQueue;

    public void setParam(String key, String value){
        this.param.put(key, value);
    }

    public static HttpRequest createMockUrl(String mock){
        return builder()
                .method(CustomHttpMethod.GET)
                .url(mock)
                .protocol("protocol")
                .queryString(new HashMap<>())
                .header(new HashMap<>())
                .bodyMap(new LinkedTreeMap<>())
                .errorQueue(new LinkedList<>())
                .build();
    }
}
