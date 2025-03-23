package simple.server;

import simple.httpRequest.SimpleHttpRequest;
import simple.logger.SingleThreadRuntimeLogger;
import simple.logger.Logger;
import simple.httpRequest.SimpleHttpRequestDTO;
import simple.parser.RequestParser;
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
                // 추가 요청 블로킹
                Socket clientSocket = serverSocket.accept();
//                if (clientSocket.isConnected()) {
//                    System.out.println("3-Way Handshake 완료됨");
//                }

                InputStream clientInputStream = clientSocket.getInputStream();
                BufferedReader request = new BufferedReader(new InputStreamReader(clientInputStream));
                SimpleHttpRequestDTO httpRequestDTO = new SimpleHttpRequestDTO();
                Logger logger = new SingleThreadRuntimeLogger();
                RequestParser httpRequestParser = new RequestParser(httpRequestDTO, logger);

                SimpleHttpRequest simpleHttpRequest = httpRequestParser.parsing(request);

                if(simpleHttpRequest.isHandshake()){
                    logger.add("handshake 요청");
                    logger.print();
                    request.close();
                    clientSocket.close();
                    continue;
                }

                RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();
                RequestHandler handler = requestHandlerFactory.getHandler(simpleHttpRequest);
                handler.sendResponse(clientSocket.getOutputStream() , getMap.get(simpleHttpRequest.getUrl()), simpleHttpRequest);

                logger.print();
                request.close();
                clientSocket.close();
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
