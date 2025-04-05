package simple.server;


import simple.constant.ApplicationSettingFlags;
import simple.httpResponse.ILambdaHandlerWrapper;

import java.io.IOException;

public interface IServer {

    void run(int port) throws IOException;

    void use(ApplicationSettingFlags applicationSettingFlags);

    void use(ApplicationSettingFlags applicationSettingFlags, String option);

    void get(String URL, ILambdaHandlerWrapper responseSuccessHandler);

    void get(String URL, ILambdaHandlerWrapper responseSuccessHandler, Class<?> clazz);

    void post(String URL, ILambdaHandlerWrapper responseSuccessHandler);
}
