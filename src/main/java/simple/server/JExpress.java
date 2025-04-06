package simple.server;

import simple.constant.ApplicationSettingFlags;
import simple.config.ApplicationConfig;
import simple.httpRequest.HttpRequest;
import simple.logger.RequestLogger;
import simple.logger.ILogger;
import simple.mapper.GetMapper;
import simple.mapper.IMapper;
import simple.mapper.MapperResolver;
import simple.mapper.PostMapper;
import simple.middleware.Cors;
import simple.parser.IHttpRequestParser;
import simple.parser.HttpRequestCharParser;
import simple.context.ApplicationContext;
import simple.requestHandler.IRequestHandler;
import simple.provider.RequestHandlerProvider;
import simple.httpResponse.ILambdaHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class JExpress implements IServer {

    private final IMapper getMapper = GetMapper.getInstance();
    private final IMapper postMapper = PostMapper.getInstance();
    private final ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
    private final Cors cors = Cors.getInstance();
    private final int threadPool;

    public JExpress() {
        this.threadPool = 1;
    }

    public JExpress(int threadPool) {
        if(threadPool<2){
            this.threadPool = 1;
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
    public void get(String url, ILambdaHandler responseSuccessHandler) {
        getMapper.addUrl(url, responseSuccessHandler);
    }

    @Override
    public void get(String url, ILambdaHandler responseSuccessHandler, Class<?> clazz) {
        getMapper.addUrl(url, responseSuccessHandler, clazz);
    }

    @Override
    public void post(String url, ILambdaHandler responseSuccessHandler) {
        postMapper.addUrl(url, responseSuccessHandler);
    }

    @Override
    public void post(String url, ILambdaHandler responseSuccessHandler, Class<?> clazz){
        postMapper.addUrl(url, responseSuccessHandler, clazz);
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
                    OutputStream clientOutputStream = clientSocket.getOutputStream();

                    HttpRequest httpRequest = requestIHttpRequestParser.parsing(request);

                    RequestHandlerProvider requestHandlerProvider = RequestHandlerProvider.getInstance();
                    IRequestHandler handler = requestHandlerProvider.getHandler(httpRequest);

                    MapperResolver mapperResolver = ApplicationContext.getMapperResolver();
                    IMapper mapper = mapperResolver.resolveMapper(httpRequest);
                    ILambdaHandler ILambdaHandler = mapper.getLambdaHandler(httpRequest);

                    handler.sendResponse(clientOutputStream, ILambdaHandler, httpRequest);

                    ILogger.print();
                }
            }
        }
    }
}
