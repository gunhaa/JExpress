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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

                // 뭔가 문제있음
                SimpleHttpRequest simpleHttpRequest = httpRequestParser.parsing(request);
//                RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();
//                RequestHandler handler = requestHandlerFactory.getHandler(simpleHttpRequest);
//                handler.sendResponse(clientSocket.getOutputStream() , getMap.get(simpleHttpRequest.getUrl()), simpleHttpRequest);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

                writer.print("HTTP/1.1 200 OK\r\n");
                writer.print("Server: SimpleHttpServer/1.0\r\n");
                writer.print("Content-Type: text/html; charset=UTF-8\r\n");

                String responseBody = "<html><body><h1>Hello, World!</h1><p>Requested path: </p></body></html>";
                writer.print("Content-Length: " + responseBody.length() + "\r\n");

                writer.print("Connection: close\r\n");

                writer.print("\r\n");

                writer.print(responseBody);
                writer.flush();

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
