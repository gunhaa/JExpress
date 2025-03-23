package simple.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import simple.httpRequest.SimpleHttpRequest;
import simple.httpRequest.SimpleHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {
    private final SimpleHttpRequestDTO httpRequestDTO;
    private final Logger logger;
    private final StringBuilder lineBuilder;

    public RequestParser(SimpleHttpRequestDTO httpRequestDTO, Logger logger) {
        this.httpRequestDTO = httpRequestDTO;
        this.logger = logger;
        this.lineBuilder = new StringBuilder();
    }

    public SimpleHttpRequest parsing(BufferedReader request) throws IOException {
        int ch;
        String line;

        while (request.ready() && (ch = request.read()) != -1) {
            System.out.println("parsing : " + (char) ch);
            if ((char) ch != '\r' && (char) ch != '\n') {
                lineBuilder.append((char) ch);
            } else if ((char) ch == '\r') {
                continue;
            } else {

                if(lineBuilder.isEmpty()){
                    httpRequestDTO.setParsingHeaders(false);
                    httpRequestDTO.setParsingBody(true);
                    lineBuilder.setLength(0);
                    continue;
                }

                line = lineBuilder.toString();
                processRequestLine(line);
                lineBuilder.setLength(0);
            }
        }
        if (httpRequestDTO.isParsingBody() && !lineBuilder.isEmpty()) {
            httpRequestDTO.addRequestBody(lineBuilder.toString());
        }

        if(!httpRequestDTO.getBody().isEmpty()){
            httpRequestDTO.parsingJsonToMap(httpRequestDTO.getBody());
        }

        return SimpleHttpRequest.builder()
                .method(httpRequestDTO.getMethod())
                .url(httpRequestDTO.getUrl())
                .protocol(httpRequestDTO.getProtocol())
                .queryString(httpRequestDTO.getQueryString())
                .header(httpRequestDTO.getHeader())
                .bodyMap(httpRequestDTO.getBodyMap())
                .build();
    }

    private void processRequestLine(String line) {
        if (httpRequestDTO.isRequestLineParsed()) {
            httpRequestDTO.parsingRequestLine(line);
            httpRequestDTO.setRequestLineParsed(false);
            httpRequestDTO.setParsingHeaders(true);
            return;
        }

        if (httpRequestDTO.isParsingHeaders()) {
            httpRequestDTO.addHeader(line);
        }

        if (httpRequestDTO.isParsingBody()) {
            httpRequestDTO.addRequestBody(line);
        }
    }

}
