package simple.server;


import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.IOException;

public interface Server {

    void run(int port) throws IOException;

    void get(String URL, ResponseSuccess responseSuccess, ResponseError responseError);

    void post();
}
