package simple.server;


import simple.constant.ApplicationSetting;
import simple.response.ResponseHandler;

import java.io.IOException;

public interface Server {

    void run(int port) throws IOException;

    void use(ApplicationSetting applicationSetting);

//    void use(ApplicationSetting applicationSetting, int connectionPool);

    void get(String URL, ResponseHandler responseSuccessHandler);

    void post();
}
