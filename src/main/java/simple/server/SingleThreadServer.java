package simple.server;

import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SingleThreadServer implements Server{

    private final HashMap<String, Response> urlMap = new HashMap<>();

    @Override
    public void run(int port) throws IOException{

        try (ServerSocket serverSocket = new ServerSocket(port)){
            while(true){
                // 추가 요청 블로킹
                Socket clientSocket = serverSocket.accept();

            }

        }
    }

    @Override
    public void get(String URL, ResponseSuccess responseSuccess, ResponseError responseError) {
        urlMap.put(URL, new Response(responseSuccess, responseError));
    }

    @Override
    public void post() {

    }

}
