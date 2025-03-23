package simple.response;

import simple.constant.HttpStatus;

public class ResponseError {
    private HttpStatus httpStatusCode;

    public ResponseError(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
