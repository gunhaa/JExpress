package simple.server;


import simple.response.ResponseError;
import simple.response.ResponseSuccess;
import simple.response.ResponseSuccessHandler;

import java.io.IOException;

public interface Server {

    void run(int port) throws IOException;

    void get(String URL, ResponseSuccessHandler responseSuccessHandler);

    void post();
}
