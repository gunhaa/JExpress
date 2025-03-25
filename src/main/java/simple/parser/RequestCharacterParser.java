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

        while ( simpleCharHttpRequestDTO.getContentLength() != 0
                && (ch = request.read()) != -1) {

            logger.add((char) ch);

            if(simpleCharHttpRequestDTO.isParsingBody()){
                processHttpBody((char)ch);
                continue;
            }

            if(ch == '\r'){
                continue;
            }

            if(ch == '\n'){

                if(lineBuilder.isEmpty()){
                    simpleCharHttpRequestDTO.updateRemainingBodyLength();
                } else {
                    processHttpRequest(lineBuilder);
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
                .errorQueue(simpleCharHttpRequestDTO.getErrorQueue())
                .build();
    }

    private void processHttpRequest(StringBuilder line) {
        if (simpleCharHttpRequestDTO.isRequestLineParsed()) {
            simpleCharHttpRequestDTO.addRequestLine(line.toString());
            return;
        }

        if (simpleCharHttpRequestDTO.isParsingHeaders()) {
            simpleCharHttpRequestDTO.addHeader(line.toString());
            return;
        }

        if (simpleCharHttpRequestDTO.isParsingBody()) {
            simpleCharHttpRequestDTO.addRequestBody(line.toString());
        }
    }

    private void processHttpBody(char c){
        simpleCharHttpRequestDTO.addRequestBody(c);
    }

}
