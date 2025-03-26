package simple.server;

import simple.constant.ApplicationSetting;
import simple.context.ApplicationContext;
import simple.httpRequest.SimpleHttpRequest;
import simple.logger.SingleThreadRuntimeLogger;
import simple.logger.Logger;
import simple.parser.Parser;
import simple.parser.RequestCharacterParser;
import simple.requestHandler.RequestHandler;
import simple.factory.RequestHandlerFactory;
import simple.response.Response;
import simple.response.ResponseHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SingleThreadServer implements Server {

    private final HashMap<String, ResponseHandler> getMap = new HashMap<>();
    private final HashMap<String, Response> postMap = new HashMap<>();
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();

    @Override
    public void run(int port) throws IOException {
        System.out.println("single thread server run on port : " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                try(Socket clientSocket = serverSocket.accept();
                    InputStream clientInputStream = clientSocket.getInputStream();
                    BufferedReader request = new BufferedReader(new InputStreamReader(clientInputStream))){

                    Logger logger = new SingleThreadRuntimeLogger();
                    Parser requestParser = new RequestCharacterParser(logger);
                    SimpleHttpRequest simpleHttpRequest = requestParser.parsing(request);

                    RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();
                    RequestHandler handler = requestHandlerFactory.getHandler(simpleHttpRequest);
                    handler.sendResponse(clientSocket.getOutputStream() , getMap.get(simpleHttpRequest.getUrl()), simpleHttpRequest);

                    logger.print();
                }
            }
        }
    }

    @Override
    public void use(ApplicationSetting applicationSetting, boolean bool){
        applicationContext.setContext(applicationSetting, bool);
    }

    @Override
    public void get(String URL, ResponseHandler responseSuccessHandler) {
        getMap.put(URL, responseSuccessHandler);
    }

    @Override
    public void post() {
//        getMap.put(URL, new Response(responseSuccess, responseError));
    }

}
