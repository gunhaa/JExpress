package simple.parser;

import simple.httpRequest.SimpleHttpRequest;
import simple.httpRequest.SimpleHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {
    private final SimpleHttpRequestDTO simpleHttpRequestDTO;
    private final Logger logger;
    private final StringBuilder lineBuilder;

    public RequestParser(Logger logger) {
        this.simpleHttpRequestDTO = new SimpleHttpRequestDTO();
        this.logger = logger;
        this.lineBuilder = new StringBuilder();
    }

    public SimpleHttpRequest parsing(BufferedReader request) throws IOException {
        int ch;
        String line;

        while (request.ready() && (ch = request.read()) != -1) {
            logger.add((char) ch);
            if ((char) ch != '\r' && (char) ch != '\n') {
                lineBuilder.append((char) ch);
            } else if ((char) ch == '\r') {
                continue;
            } else {

                if(lineBuilder.isEmpty()){
                    simpleHttpRequestDTO.setParsingHeaders(false);
                    simpleHttpRequestDTO.setParsingBody(true);
                    lineBuilder.setLength(0);
                    continue;
                }

                line = lineBuilder.toString();
                processRequestLine(line);
                lineBuilder.setLength(0);
            }
        }
        if (simpleHttpRequestDTO.isParsingBody() && !lineBuilder.isEmpty()) {
            simpleHttpRequestDTO.addRequestBody(lineBuilder.toString());
        }

        if(!simpleHttpRequestDTO.getBody().isEmpty()){
            simpleHttpRequestDTO.parsingJsonToMap(simpleHttpRequestDTO.getBody());
        }

        return SimpleHttpRequest.builder()
                .method(simpleHttpRequestDTO.getMethod())
                .url(simpleHttpRequestDTO.getUrl())
                .protocol(simpleHttpRequestDTO.getProtocol())
                .queryString(simpleHttpRequestDTO.getQueryString())
                .header(simpleHttpRequestDTO.getHeader())
                .bodyMap(simpleHttpRequestDTO.getBodyMap())
                .build();
    }

    private void processRequestLine(String line) {
        if (simpleHttpRequestDTO.isRequestLineParsed()) {
            simpleHttpRequestDTO.parsingRequestLine(line);
            simpleHttpRequestDTO.setRequestLineParsed(false);
            simpleHttpRequestDTO.setParsingHeaders(true);
            return;
        }

        if (simpleHttpRequestDTO.isParsingHeaders()) {
            simpleHttpRequestDTO.addHeader(line);
            return;
        }

        if (simpleHttpRequestDTO.isParsingBody()) {
            simpleHttpRequestDTO.addRequestBody(line);
        }
    }

}
