package simple.response;

public class Response {

    private ResponseSuccess responseSuccess;
    private ResponseError responseError;

    public Response(ResponseSuccess responseSuccess, ResponseError responseError) {
        this.responseSuccess = responseSuccess;
        this.responseError = responseError;
    }

    public ResponseSuccess getResponseSuccess() {
        return responseSuccess;
    }

    public ResponseError getResponseError() {
        return responseError;
    }
}
