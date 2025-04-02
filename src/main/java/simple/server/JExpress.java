package simple.server;

import simple.constant.ApplicationSetting;
import simple.config.ApplicationConfig;
import simple.httpRequest.HttpRequest;
import simple.logger.RequestLogger;
import simple.logger.Logger;
import simple.mapper.GetMapper;
import simple.mapper.Mapper;
import simple.middleware.Cors;
import simple.parser.Parser;
import simple.parser.RequestCharacterParser;
import simple.context.ApplicationContext;
import simple.requestHandler.RequestHandler;
import simple.provider.RequestHandlerProvider;
import simple.response.LambdaHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class JExpress implements Server {

    private final Mapper getMap = GetMapper.getInstance();
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
    public void get(String URL, LambdaHandler responseSuccessHandler) {
        getMap.addUrl(URL, responseSuccessHandler);
    }

    @Override
    public void get(String URL, LambdaHandler responseSuccessHandler, Class<?> clazz) {
        getMap.addUrl(URL, responseSuccessHandler);
    }

    @Override
    public void post(String URL, LambdaHandler responseSuccessHandler) {
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

                    Logger logger = new RequestLogger();
                    Parser requestParser = new RequestCharacterParser(logger);

                    HttpRequest httpRequest = requestParser.parsing(request);

                    RequestHandlerProvider requestHandlerProvider = RequestHandlerProvider.getInstance();
                    RequestHandler handler = requestHandlerProvider.getHandler(httpRequest);

                    handler.sendResponse(clientSocket.getOutputStream() , getMap.getHandler(httpRequest.getUrl()), httpRequest);

                    logger.print();
                }
            }
        }
    }


}
