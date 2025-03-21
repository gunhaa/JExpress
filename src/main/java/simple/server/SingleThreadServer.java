package simple.server;

import simple.logger.SingleThreadRuntimeLogger;
import simple.logger.Logger;
import simple.httpRequest.HttpRequestDTO;
import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static simple.constant.Constant.*;

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


                String line;
                HttpRequestDTO httpRequestDTO = new HttpRequestDTO();
                Logger log = new SingleThreadRuntimeLogger();

                /*
                GET /hello HTTP/1.1
                Host: localhost:8080
                User-Agent: Java-HttpClient

                POST /data HTTP/1.1
                Host: localhost:8080
                User-Agent: Java-HttpClient
                Content-Type: application/json
                Content-Length: 28

                { "message": "Hello, Server!" }
                */

                while ((line = request.readLine()) != null && !line.isEmpty()) {

                    log.add(line);

                    // logic
                    if(httpRequestDTO.isParsingHeaders()){
                        httpRequestDTO.addHeader(line);
                    }

                    if(httpRequestDTO.isRequestLineParsed()){
                        httpRequestDTO.setRequestLine(line);
                        String[] requestLine = line.split(" ");
                        String httpMethod = requestLine[0];
//                        String httpUrl = requestLine[1];
//                        RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();

                        if(httpMethod.equals(HTTP_METHOD_GET)){
//                            RequestHandler handler = requestHandlerFactory.getHandler(httpMethod);
                            // HttpRequest 객체를 만들어내야함
//                            httpRequestDTO.setRe
//                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                        Response userCustomResponse = getMap.get(httpUrl);
//                        handler.handleResponse(out, userCustomResponse);
                        }

                        if(httpMethod.equals(HTTP_METHOD_POST)){

                        }
                        httpRequestDTO.setRequestLineParsed(false);
                        httpRequestDTO.setParsingHeaders(true);
                    }


                    if(line.equals("")){
                        httpRequestDTO.setParsingHeaders(false);
                        httpRequestDTO.setParsingBody(true);
                        System.out.println("\r\n 연락 호출");
                    }

                    if(httpRequestDTO.isParsingBody()){
                        httpRequestDTO.addRequestBody(line);
                    }

                }
                log.print();
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
