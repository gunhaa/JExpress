package simple.server;

import simple.constant.Constant;
import simple.requestHandler.RequestHandler;
import simple.requestHandler.RequestHandlerFactory;
import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

                BufferedReader line = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                String tempString;
                StringBuilder sb = new StringBuilder();
                while ((tempString = line.readLine()) != null && !tempString.isEmpty()) {
                    sb.append(tempString).append("\n");
                    String[] httpFirstLine = tempString.split(" ");
                    String httpMethod = httpFirstLine[0];
                    String httpUrl = httpFirstLine[1];
                    RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();

                    if(httpMethod.equals(HTTP_METHOD_GET) || httpMethod.equals(HTTP_METHOD_POST)){
                        RequestHandler handler = requestHandlerFactory.getHandler(httpMethod);
                        // HttpRequest 객체를 만들어내야함
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                        Response userCustomResponse = getMap.get(httpUrl);
                        handler.handleResponse(out, userCustomResponse);
                    }
                }

                System.out.println(sb);
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
