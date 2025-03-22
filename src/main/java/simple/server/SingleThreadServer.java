package simple.server;

import simple.httpRequest.SimpleHttpRequest;
import simple.logger.SingleThreadRuntimeLogger;
import simple.logger.Logger;
import simple.httpRequest.SimpleHttpRequestDTO;
import simple.parser.RequestParser;
import simple.requestHandler.RequestHandler;
import simple.requestHandler.RequestHandlerFactory;
import simple.response.Response;
import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                System.out.println("client ip : " + clientSocket.getLocalPort());

                BufferedReader request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                SimpleHttpRequestDTO httpRequestDTO = new SimpleHttpRequestDTO();
                Logger logger = new SingleThreadRuntimeLogger();
                RequestParser httpRequestParser = new RequestParser(httpRequestDTO, logger);

                SimpleHttpRequest simpleHttpRequest = httpRequestParser.parsing(request);

                RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();
                RequestHandler handler = requestHandlerFactory.getHandler(simpleHttpRequest);

//                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                        Response userCustomResponse = getMap.get(httpUrl);
//                        handler.handleResponse(out, userCustomResponse);
//                        }

//                        if(httpMethod.equals(HTTP_METHOD_POST)){
//
//                        }

                System.out.println("바디는 이것임 : "+ httpRequestDTO.getBody().toString());
                System.out.println("qs는 이거임 : " + httpRequestDTO.getQueryString().get("asd"));
//                logger.print();
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
