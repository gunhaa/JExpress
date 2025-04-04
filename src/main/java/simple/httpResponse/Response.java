package simple.httpResponse;

public class Response {

    private ResponseSuccess responseSuccess;

    public Response(ResponseSuccess responseSuccess) {
        this.responseSuccess = responseSuccess;
    }

    public ResponseSuccess getResponseSuccess() {
        return responseSuccess;
    }

}
