package simple.parser;

import simple.httpRequest.SimpleHttpRequest;
import simple.httpRequest.SimpleCharHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestCharacterParser implements Parser{
    private final SimpleCharHttpRequestDTO simpleCharHttpRequestDTO;
    private final Logger logger;
    private final StringBuilder lineBuilder;

    public RequestCharacterParser(Logger logger) {
        this.simpleCharHttpRequestDTO = new SimpleCharHttpRequestDTO();
        this.logger = logger;
        this.lineBuilder = new StringBuilder();
    }

    @Override
    public SimpleHttpRequest parsing(BufferedReader request) throws IOException {
        int ch;

        // request.ready() 부분 문제 있음
        while (request.ready() && (ch = request.read()) != -1) {

            logger.add((char) ch);

            if(ch == '\r'){
                continue;
            }

            if(ch == '\n'){
                if(lineBuilder.isEmpty()){
                    simpleCharHttpRequestDTO.setParsingHeaders(false);
                    simpleCharHttpRequestDTO.setParsingBody(true);
                } else {
                    processRequestLine(lineBuilder.toString());
                }
                lineBuilder.setLength(0);
            } else {
                lineBuilder.append((char) ch);
            }

        }
        if (simpleCharHttpRequestDTO.isParsingBody() && !lineBuilder.isEmpty()) {
            simpleCharHttpRequestDTO.addRequestBody(lineBuilder.toString());
        }

        if(!simpleCharHttpRequestDTO.getBody().isEmpty()){
            simpleCharHttpRequestDTO.parsingJsonToMap(simpleCharHttpRequestDTO.getBody());
        }

        return SimpleHttpRequest.builder()
                .method(simpleCharHttpRequestDTO.getMethod())
                .url(simpleCharHttpRequestDTO.getUrl())
                .protocol(simpleCharHttpRequestDTO.getProtocol())
                .queryString(simpleCharHttpRequestDTO.getQueryString())
                .header(simpleCharHttpRequestDTO.getHeader())
                .bodyMap(simpleCharHttpRequestDTO.getBodyMap())
                .build();
    }

    private void processRequestLine(String line) {
        if (simpleCharHttpRequestDTO.isRequestLineParsed()) {
            simpleCharHttpRequestDTO.parsingRequestLine(line);
            simpleCharHttpRequestDTO.setRequestLineParsed(false);
            simpleCharHttpRequestDTO.setParsingHeaders(true);
            return;
        }

        if (simpleCharHttpRequestDTO.isParsingHeaders()) {
            simpleCharHttpRequestDTO.addHeader(line);
            return;
        }

        if (simpleCharHttpRequestDTO.isParsingBody()) {
            simpleCharHttpRequestDTO.addRequestBody(line);
        }
    }

}
