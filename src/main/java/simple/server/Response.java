package simple.server;

import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

public class Response {

    private ResponseSuccess responseSuccess;
    private ResponseError responseError;

    public Response(ResponseSuccess responseSuccess, ResponseError responseError) {
        this.responseSuccess = responseSuccess;
        this.responseError = responseError;
    }
}
