package simple.response;

import simple.httpMethod.HttpStatus;

public class ResponseError {
    private HttpStatus httpStatusCode;
    private String errorMessage;

    public ResponseError(HttpStatus httpStatusCode, String errorMessage) {
        this.httpStatusCode = httpStatusCode;
        this.errorMessage = errorMessage;
    }
}
