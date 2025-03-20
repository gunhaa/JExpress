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
                while ((tempString = line.readLine()) != null && !tempString.isEmpty()) {
                    if (tempString.isEmpty()) {
                        break;
                    }

                    String[] temp = tempString.split(" ");
                    RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();
                    if(temp[0].equals(Constant.HTTP_METHOD_GET)){
                        RequestHandler handler = requestHandlerFactory.getHandler(temp[0]);
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                        Response userCustomResponse = getMap.get(temp[1]);
                        handler.handleResponse(out, userCustomResponse);
                    }


//                    if(userCustomResponse == null){
//                        boolean keepAlive = clientSocket.getKeepAlive();
//                        System.out.println(keepAlive);
//                        System.out.println("null이 들어왔음");
//                    }

//                    if (temp[0].equals(HTTP_METHOD_GET)) {
//                        System.out.println("GET 로직 실행");
//                        Response response = getMap.get(temp[1]);
//                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                        response.getResponseSuccess().responseParser(out);
//                        break;
//                    } else if (temp[0].equals(HTTP_METHOD_POST)) {
//                        System.out.println("POST 로직 실행");
//                    }

                }

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
