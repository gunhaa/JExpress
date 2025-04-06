package simple.server;


import simple.constant.ApplicationSettingFlags;
import simple.lambda.ILambdaHandler;

import java.io.IOException;

public interface IServer {

    void run(int port) throws IOException;

    void use(ApplicationSettingFlags applicationSettingFlags);

    void use(ApplicationSettingFlags applicationSettingFlags, String option);

    void get(String URL, ILambdaHandler responseSuccessHandler);

    void get(String URL, ILambdaHandler responseSuccessHandler, Class<?> clazz);

    void post(String URL, ILambdaHandler responseSuccessHandler);

    void post(String URL, ILambdaHandler responseSuccessHandler, Class<?> clazz);
}
