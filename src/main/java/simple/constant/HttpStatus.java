package simple.constant;

public enum HttpStatus {
    OK_200(200, "OK"),
    CREATED_201(201, "Created"),
    BAD_REQUEST_400(400, "Bad Request"),
    NOT_FOUND_404(404, "Not Found"),
    INTERNAL_SERVER_ERROR_500(500, "Internal Server Error");

    private final int statusCode;
    private final String message;

    HttpStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
