package simple.apiDocs;

import simple.constant.CustomHttpMethod;

import java.util.HashMap;
import java.util.Map;

public class ApiDetails {

    private final CustomHttpMethod customHttpMethod;
    private final String url;
    private final String returnType;
    private Map<String, String> fields = new HashMap<>();

    public ApiDetails(CustomHttpMethod customHttpMethod, String url, String returnType) {
        this.customHttpMethod = customHttpMethod;
        this.url = url;
        this.returnType = returnType;
    }

    public void addField(String fieldName, String fieldType){
        fields.put(fieldName, fieldType);
    }
}
