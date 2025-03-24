package simple.parser;

import simple.httpRequest.SimpleHttpRequest;
import simple.httpRequest.SimpleHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestCharacterParser implements Parser{
    private final SimpleHttpRequestDTO simpleHttpRequestDTO;
    private final Logger logger;
    private final StringBuilder lineBuilder;

    public RequestCharacterParser(Logger logger) {
        this.simpleHttpRequestDTO = new SimpleHttpRequestDTO();
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
                    simpleHttpRequestDTO.setParsingHeaders(false);
                    simpleHttpRequestDTO.setParsingBody(true);
                } else {
                    processRequestLine(lineBuilder.toString());
                }
                lineBuilder.setLength(0);
            } else {
                lineBuilder.append((char) ch);
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
