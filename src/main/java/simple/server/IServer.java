package simple.server;


import simple.constant.ApplicationSetting;
import simple.httpResponse.ILambdaHandler;

import java.io.IOException;

public interface IServer {

    void run(int port) throws IOException;

    void use(ApplicationSetting applicationSetting);

    void use(ApplicationSetting applicationSetting, String option);

    void get(String URL, ILambdaHandler responseSuccessHandler);

    void get(String URL, ILambdaHandler responseSuccessHandler, Class<?> clazz);

    void post(String URL, ILambdaHandler responseSuccessHandler);
}
