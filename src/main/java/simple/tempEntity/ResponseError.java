package simple.tempEntity;

public class ResponseError {
    private int statusCode;
    private String errorMessage;

    public ResponseError(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
