package simple.server;


import simple.response.ResponseHandler;

import java.io.IOException;

public interface Server {

    void run(int port) throws IOException;

    void get(String URL, ResponseHandler responseSuccessHandler);

    void post();
}
