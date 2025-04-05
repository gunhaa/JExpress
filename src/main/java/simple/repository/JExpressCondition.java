package simple.repository;

import lombok.Getter;

@Getter
public class JExpressCondition {
    private final String columnName;
    private final String value;

    public JExpressCondition(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }
}
