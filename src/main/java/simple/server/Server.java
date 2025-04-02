package simple.server;


import simple.constant.ApplicationSetting;
import simple.response.LambdaHandler;

import java.io.IOException;

public interface Server {

    void run(int port) throws IOException;

    void use(ApplicationSetting applicationSetting);

    void use(ApplicationSetting applicationSetting, String option);

    void get(String URL, LambdaHandler responseSuccessHandler);

    void get(String URL, LambdaHandler responseSuccessHandler, Class<?> clazz);

    void post(String URL, LambdaHandler responseSuccessHandler);
}
