package simple.server;

import simple.logger.JavaSingleThreadRuntimeLogger;
import simple.logger.Logger;
import simple.requestHandler.RequestHandler;
import simple.requestHandler.RequestHandlerFactory;
import simple.requestHandler.SimpleHttpRequestDTO;
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
                SimpleHttpRequestDTO simpleHttpRequest = new SimpleHttpRequestDTO();
                Logger log = new JavaSingleThreadRuntimeLogger();

                while ((line = request.readLine()) != null && !line.isEmpty()) {

                    log.add(line);

                    if(simpleHttpRequest.isRequestLine()){
                        simpleHttpRequest.setRequestLine(line);
                        String[] httpFirstLine = line.split(" ");
                        String httpMethod = httpFirstLine[0];
                        String httpUrl = httpFirstLine[1];
                        RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();

                        if(httpMethod.equals(HTTP_METHOD_GET)){
                            RequestHandler handler = requestHandlerFactory.getHandler(httpMethod);
                            // HttpRequest 객체를 만들어내야함
//                            simpleHttpRequest.setRe
//                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                        Response userCustomResponse = getMap.get(httpUrl);
//                        handler.handleResponse(out, userCustomResponse);
                        }

                        if(httpMethod.equals(HTTP_METHOD_POST)){
                            if(line.equals("\n")){
                                simpleHttpRequest.setIsRequestBody(true);
                            }

                            if(simpleHttpRequest.isRequestBody()){
//                                body.append(line).append("\n");
                            }
                        }
                        simpleHttpRequest.setIsFirstLine(false);
                    }
                    // logic
                    if(simpleHttpRequest.isHeader()){
                        simpleHttpRequest.addHeader(line);
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
