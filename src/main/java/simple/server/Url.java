package simple.server;

import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

public class Url {

    private String url;
    private ResponseSuccess resSuccess;
    private ResponseError resError;

    public Url(String url, ResponseSuccess resSuccess, ResponseError resError) {
        this.url = url;
        this.resSuccess = resSuccess;
        this.resError = resError;
    }
}
