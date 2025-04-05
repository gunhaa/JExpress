package simple.server;

import simple.constant.ApplicationSettingFlags;
import simple.config.ApplicationConfig;
import simple.httpRequest.HttpRequest;
import simple.logger.RequestLogger;
import simple.logger.ILogger;
import simple.mapper.GetIMapper;
import simple.mapper.IMapper;
import simple.middleware.Cors;
import simple.parser.IHttpRequestParser;
import simple.parser.HttpRequestCharParser;
import simple.context.ApplicationContext;
import simple.requestHandler.IRequestHandler;
import simple.provider.RequestHandlerProvider;
import simple.httpResponse.ILambdaHandlerWrapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JExpress implements IServer {

    private final IMapper getMap = GetIMapper.getInstance();
    private final ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
    private final Cors cors = Cors.getInstance();
    private final int threadPool;

    public JExpress() {
        this.threadPool = 0;
    }

    public JExpress(int threadPool) {
        if(threadPool<2){
            this.threadPool = 0;
        } else {
            this.threadPool = threadPool;
        }
    }

    @Override
    public void use(ApplicationSettingFlags applicationSettingFlags){
        applicationConfig.registerConfig(applicationSettingFlags);
    }

    @Override
    public void use(ApplicationSettingFlags applicationSettingFlags, String option){
        applicationConfig.registerConfig(applicationSettingFlags);
        cors.registerCorsValue(applicationSettingFlags, option);
    }

    @Override
    public void get(String URL, ILambdaHandlerWrapper responseSuccessHandler) {
        getMap.addUrl(URL, responseSuccessHandler);
    }

    @Override
    public void get(String URL, ILambdaHandlerWrapper responseSuccessHandler, Class<?> clazz) {
        getMap.addUrl(URL, responseSuccessHandler, clazz);
    }

    @Override
    public void post(String URL, ILambdaHandlerWrapper responseSuccessHandler) {
//        getMap.put(URL, new Response(responseSuccess, responseError));
    }


    @Override
    public void run(int port) throws IOException {

        ApplicationContext.initializeApplicationContext();

        System.out.println("server port : " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                try(Socket clientSocket = serverSocket.accept();
                    InputStream clientInputStream = clientSocket.getInputStream();
                    BufferedReader request = new BufferedReader(new InputStreamReader(clientInputStream))){

                    ILogger ILogger = new RequestLogger();
                    IHttpRequestParser requestIHttpRequestParser = new HttpRequestCharParser(ILogger);

                    HttpRequest httpRequest = requestIHttpRequestParser.parsing(request);

                    RequestHandlerProvider requestHandlerProvider = RequestHandlerProvider.getInstance();
                    IRequestHandler handler = requestHandlerProvider.getHandler(httpRequest);
                    ILambdaHandlerWrapper ILambdaHandlerWrapper = getMap.getLambdaHandler(httpRequest);

                    handler.sendResponse(clientSocket.getOutputStream(), ILambdaHandlerWrapper, httpRequest);

                    ILogger.print();
                }
            }
        }
    }
}
