package simple.server;

import simple.constant.ApplicationSettingFlags;
import simple.config.ApplicationConfig;
import simple.httpRequest.HttpRequest;
import simple.logger.LoggerFactory;
import simple.logger.LoggerManager;
import simple.logger.RequestLogger;
import simple.logger.ILogger;
import simple.mapper.GetMapper;
import simple.mapper.IMapper;
import simple.context.MapperResolver;
import simple.mapper.PostMapper;
import simple.middleware.Cors;
import simple.middleware.MiddlewareProvider;
import simple.parser.IHttpRequestParser;
import simple.parser.HttpRequestCharParser;
import simple.context.ApplicationContext;
import simple.requestHandler.IRequestHandler;
import simple.provider.RequestHandlerProvider;
import simple.lambda.ILambdaHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        if (threadPool < 2) {
            this.threadPool = 1;
        } else {
            this.threadPool = threadPool;
        }
    }

    @Override
    public void use(ApplicationSettingFlags applicationSettingFlags) {
        applicationConfig.registerConfig(applicationSettingFlags);
    }

    @Override
    public void use(ApplicationSettingFlags applicationSettingFlags, String option) {
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
    public void post(String url, ILambdaHandler responseSuccessHandler, Class<?> clazz) {
        postMapper.addUrl(url, responseSuccessHandler, clazz);
    }


    @Override
    public void run(String port) throws IOException {

        ApplicationContext.initialize(
                GetMapper.getInstance(),
                PostMapper.getInstance(),
                MiddlewareProvider.getInstance(),
                ApplicationConfig.getInstance()
        );
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        applicationContext.initializeMiddleWare();

        ExecutorService threadPool = Executors.newFixedThreadPool(this.threadPool);

        System.out.println("server port : " + port);
        System.out.println("thread pool : " + this.threadPool);

        int portInteger = Integer.parseInt(port);
        try (ServerSocket serverSocket = new ServerSocket(portInteger)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleClient(clientSocket, applicationContext));
            }
        } finally {
            threadPool.shutdown();
        }
    }

    private void handleClient(Socket clientSocket, ApplicationContext applicationContext) {
        try (InputStream clientInputStream = clientSocket.getInputStream();
             BufferedReader request = new BufferedReader(new InputStreamReader(clientInputStream))) {

            LoggerManager loggerManager = applicationContext.getLoggerManager();

//            ILogger logger = new RequestLogger();

            IHttpRequestParser requestIHttpRequestParser = new HttpRequestCharParser(loggerManager);
            OutputStream clientOutputStream = clientSocket.getOutputStream();

            HttpRequest httpRequest = requestIHttpRequestParser.parsing(request);

            MapperResolver mapperResolver = applicationContext.getMapperResolver();
            IMapper mapper = mapperResolver.resolveMapper(httpRequest);
            ILambdaHandler ILambdaHandler = mapper.getLambdaHandler(httpRequest);

            RequestHandlerProvider requestHandlerProvider = RequestHandlerProvider.getInstance();
            IRequestHandler handler = requestHandlerProvider.getHandler(httpRequest);

            handler.sendResponse(clientOutputStream, httpRequest, ILambdaHandler);

            loggerManager.printAll();
        } catch (IOException e) {
            System.err.println("handleClient IO Exception");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("clientSocket IO Exception");
            }
        }
    }
}
