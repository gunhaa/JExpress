package simple.apiDocs;

import java.util.HashMap;
import java.util.Map;

public class ApiDetails {

    private final String url;
    private final String returnType;
    private Map<String, String> fields = new HashMap<>();

    public ApiDetails(String url, String returnType) {
        this.url = url;
        this.returnType = returnType;
    }

    public void addField(String fieldName, String fieldType){
        fields.put(fieldName, fieldType);
    }
}
