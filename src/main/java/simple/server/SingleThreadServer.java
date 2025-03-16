package simple.server;

import com.google.gson.Gson;
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

                    String[] temp = tempString.split(" ");
                    if (temp[0].equals("GET")) {
                        System.out.println("GET 로직 실행");
                        Response response = getMap.get(temp[1]);
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                        response.getResponseSuccess().responseParser(out);
                        break;
                    } else if (temp[0].equals("POST")) {
                        System.out.println("POST 로직 실행");
                    }

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
