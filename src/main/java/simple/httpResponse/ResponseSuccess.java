package simple.httpResponse;

import lombok.Getter;
import simple.constant.HttpStatus;

@Getter
public class ResponseSuccess {

    private final HttpStatus userCustomHttpStatus;
    private final Object entity;

    public ResponseSuccess(HttpStatus userCustomHttpStatus, Object entity) {
        this.userCustomHttpStatus = userCustomHttpStatus;
        this.entity = entity;
    }

}
