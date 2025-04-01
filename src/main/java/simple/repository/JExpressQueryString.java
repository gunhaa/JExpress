package simple.repository;

import lombok.Getter;

@Getter
public class JExpressQueryString {
    private String key;
    private String value;

    public JExpressQueryString(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
