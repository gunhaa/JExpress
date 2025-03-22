package simple.server;


import simple.response.ResponseError;
import simple.response.ResponseSuccess;

import java.io.IOException;

public interface Server {

    void run(int port) throws IOException;

    void get(String URL, ResponseSuccess responseSuccess, ResponseError responseError);

    void post();
}
