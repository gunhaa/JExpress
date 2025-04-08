package simple.apiDocs;

import lombok.Getter;
import simple.constant.CustomHttpMethod;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiDetail {

    private final CustomHttpMethod customHttpMethod;
    private final String url;
    private final String returnType;
    private final Map<String, String> fields = new HashMap<>();

    public ApiDetail(CustomHttpMethod customHttpMethod, String url, String returnType) {
        this.customHttpMethod = customHttpMethod;
        this.url = url;
        this.returnType = returnType;
    }

    public void addField(Class<?> clazz){
        switch (getClassCategory(clazz)) {
            case "기본 타입" -> addField("기본 타입", clazz.getSimpleName());
            case "배열 타입" -> addField("배열 타입", clazz.getSimpleName());
            case "표준 자바 클래스" -> addField("표준 자바 클래스", clazz.getSimpleName());
            default -> addField("오류 발생 확인 필요", clazz.getSimpleName());
        }
    }

    private String getClassCategory(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return "기본 타입";
        } else if (clazz.isArray()) {
            return "배열 타입";
        } else if (clazz.getPackage() != null && clazz.getPackage().getName().startsWith("java")) {
            return "표준 자바 클래스";
        } else {
            return "오류 발생 확인 필요";
        }
    }

    public void addField(String fieldName, String fieldType){
        fields.put(fieldName, fieldType);
    }
}
