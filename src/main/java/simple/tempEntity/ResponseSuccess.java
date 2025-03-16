package simple.tempEntity;

public class ResponseSuccess {

    private int statusCode;
    private Object entity;

    public ResponseSuccess(int statusCode, Object entity) {
        this.statusCode = statusCode;
        this.entity = entity;
    }
}
