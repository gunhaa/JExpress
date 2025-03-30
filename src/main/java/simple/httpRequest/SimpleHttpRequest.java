package simple.httpRequest;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Builder;
import lombok.Getter;
import simple.constant.HttpMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Builder
@Getter
public class SimpleHttpRequest {
    private final HttpMethod method;
    private final String url;
    private final String protocol;
    private final HashMap<String, String> queryString;
    private final HashMap<String, String> header;
    private final LinkedTreeMap<String, Object> bodyMap;
    private Queue<ErrorStatus> errorQueue;

    @Deprecated
    public boolean isHandshake(){
        return this.url == null && this.method == null && this.protocol == null;
    }

    public static SimpleHttpRequest createMock(){
        return builder()
                .method(HttpMethod.GET)
                .url("url")
                .protocol("protocol")
                .queryString(new HashMap<>())
                .header(new HashMap<>())
                .bodyMap(new LinkedTreeMap<>())
                .errorQueue(new LinkedList<>())
                .build();
    }
}
