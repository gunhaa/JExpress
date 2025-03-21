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

                StringBuilder lineBuilder = new StringBuilder();
                String line;
                int ch;
                HttpRequestDTO httpRequestDTO = new HttpRequestDTO();
                Logger log = new SingleThreadRuntimeLogger();

                while ((ch = request.read()) != -1) {

                    log.add((char) ch);

                    if((char) ch != '\r' && (char) ch != '\n'){
                        lineBuilder.append((char)ch);
                        continue;
                    } else if((char)ch == '\r') {
                        continue;
                    } else {
                        line = lineBuilder.toString();

                        if(lineBuilder.isEmpty()){
                            httpRequestDTO.setParsingHeaders(false);
                            httpRequestDTO.setParsingBody(true);
                        }

                        lineBuilder.delete(0, lineBuilder.length());
                    }

                    if(httpRequestDTO.isParsingHeaders()){
                        httpRequestDTO.addHeader(line);
                    }

                    if(httpRequestDTO.isRequestLineParsed()){
//                        httpRequestDTO.setRequestLine(line);
//                        String[] requestLine = line.split(" ");
//                        String httpMethod = requestLine[0];
//                        String httpUrl = requestLine[1];
//                        RequestHandlerFactory requestHandlerFactory = RequestHandlerFactory.getInstance();

//                        if(httpMethod.equals(HTTP_METHOD_GET)){
//                            RequestHandler handler = requestHandlerFactory.getHandler(httpMethod);
                            // HttpRequest 객체를 만들어내야함
//                            httpRequestDTO.setRe
//                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                        Response userCustomResponse = getMap.get(httpUrl);
//                        handler.handleResponse(out, userCustomResponse);
//                        }

//                        if(httpMethod.equals(HTTP_METHOD_POST)){
//
//                        }
                        httpRequestDTO.setRequestLineParsed(false);
                        httpRequestDTO.setParsingHeaders(true);
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
