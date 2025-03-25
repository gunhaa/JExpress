package simple.httpRequest;

import lombok.Getter;
import simple.constant.HttpStatus;

@Getter
public class ErrorStatus {
    private final HttpStatus httpStatus;
    private final String message;

    public ErrorStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static ErrorStatus getDefaultErrorStatus(){
        return new ErrorStatus(HttpStatus.BAD_REQUEST_400, "Invalid request - unknown error");
    }
}
