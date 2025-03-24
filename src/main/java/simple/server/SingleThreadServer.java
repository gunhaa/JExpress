package simple.server;

import simple.httpRequest.SimpleHttpRequest;
import simple.logger.SingleThreadRuntimeLogger;
import simple.logger.Logger;
import simple.parser.Parser;
import simple.parser.RequestCharacterParser;
import simple.parser.RequestLineParser;
import simple.requestHandler.RequestHandler;
import simple.factory.RequestHandlerFactory;
import simple.response.Response;
import simple.response.ResponseError;
import simple.response.ResponseSuccess;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SingleThreadServer implements Server {

    private final HashMap<String, Response> getMap = new HashMap<>();
    private final HashMap<String, Response> postMap = new HashMap<>();

    @Override
    public void run(int port) throws IOException {
        System.out.println("single thread server run on port : " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                try(Socket clientSocket = serverSocket.accept();
                    InputStream clientInputStream = clientSocket.getInputStream();
                    BufferedReader request = new BufferedReader(new InputStreamReader(clientInputStream))){

                    Logger logger = new SingleThreadRuntimeLogger();
                    Parser requestParser = new RequestLineParser(logger);
                    SimpleHttpRequest simpleHttpRequest = requestParser.parsing(request);

                    // 정확한 handshake 요청 방법 파악 필요
//                    if(simpleHttpRequest.isHandshake()){
//                        logger.add("handshake 요청");
//                        logger.print();
//                        continue;
//                    }

                    RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();
                    RequestHandler handler = requestHandlerFactory.getHandler(simpleHttpRequest);
                    handler.sendResponse(clientSocket.getOutputStream() , getMap.get(simpleHttpRequest.getUrl()), simpleHttpRequest);

                    logger.print();
                }
            }
        }
    }

    @Override
    public void get(String URL, ResponseSuccess responseSuccess, ResponseError responseError) {
        getMap.put(URL, new Response(responseSuccess, responseError));
    }

    @Override
    public void post() {
//        getMap.put(URL, new Response(responseSuccess, responseError));
    }

}
