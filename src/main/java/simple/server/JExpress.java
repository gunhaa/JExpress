package simple.server;

import simple.constant.ApplicationSetting;
import simple.config.ApplicationConfig;
import simple.httpRequest.HttpRequest;
import simple.logger.RequestILogger;
import simple.logger.ILogger;
import simple.mapper.GetIMapper;
import simple.mapper.IMapper;
import simple.middleware.Cors;
import simple.parser.IHttpRequestParser;
import simple.parser.RequestCharacterIHttpRequestParser;
import simple.context.ApplicationContext;
import simple.requestHandler.IRequestHandler;
import simple.provider.RequestHandlerProvider;
import simple.httpResponse.ILambdaHandler;
import java.io.*;
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
    public void use(ApplicationSetting applicationSetting){
        applicationConfig.registerConfig(applicationSetting);
    }

    @Override
    public void use(ApplicationSetting applicationSetting, String option){
        applicationConfig.registerConfig(applicationSetting);
        cors.registerCorsValue(applicationSetting, option);
    }

    @Override
    public void get(String URL, ILambdaHandler responseSuccessHandler) {

        getMap.addUrl(URL, responseSuccessHandler);
    }

    @Override
    public void get(String URL, ILambdaHandler responseSuccessHandler, Class<?> clazz) {
        getMap.addUrl(URL, responseSuccessHandler, clazz);
    }

    @Override
    public void post(String URL, ILambdaHandler responseSuccessHandler) {
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

                    ILogger ILogger = new RequestILogger();
                    IHttpRequestParser requestIHttpRequestParser = new RequestCharacterIHttpRequestParser(ILogger);

                    HttpRequest httpRequest = requestIHttpRequestParser.parsing(request);

                    RequestHandlerProvider requestHandlerProvider = RequestHandlerProvider.getInstance();
                    IRequestHandler handler = requestHandlerProvider.getHandler(httpRequest);
                    ILambdaHandler ILambdaHandler = getMap.getLambdaHandler(httpRequest.getUrl());

                    handler.sendResponse(clientSocket.getOutputStream(), ILambdaHandler, httpRequest);

                    ILogger.print();
                }
            }
        }
    }


}
