package simple.parser;

import simple.httpRequest.SimpleHttpRequest;
import simple.httpRequest.SimpleLineHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLineParser implements Parser{
    private final SimpleLineHttpRequestDTO simpleLineHttpRequestDTO;
    private final Logger logger;

    public RequestLineParser(Logger logger) {
        this.simpleLineHttpRequestDTO = new SimpleLineHttpRequestDTO();
        this.logger = logger;
    }

    @Override
    public SimpleHttpRequest parsing(BufferedReader request) throws IOException {

        String line;
        while((line = request.readLine())!=null){
            logger.add(line);
            processRequestLine(line);
            if(simpleLineHttpRequestDTO.isParsingBody() && simpleLineHttpRequestDTO.isFinishBodyParsing()){
                StringBuilder requestBody = simpleLineHttpRequestDTO.getBody();
                simpleLineHttpRequestDTO.parsingJsonToMap(requestBody);
                break;
            }

        }

        return SimpleHttpRequest.builder()
                .method(simpleLineHttpRequestDTO.getMethod())
                .url(simpleLineHttpRequestDTO.getUrl())
                .protocol(simpleLineHttpRequestDTO.getProtocol())
                .queryString(simpleLineHttpRequestDTO.getQueryString())
                .header(simpleLineHttpRequestDTO.getHeader())
                .bodyMap(simpleLineHttpRequestDTO.getBodyMap())
                .build();
    }

    private void processRequestLine(String line) {
        if (simpleLineHttpRequestDTO.isRequestLineParsed()) {
            simpleLineHttpRequestDTO.parsingRequestLine(line);
            return;
        }

        if (simpleLineHttpRequestDTO.isParsingHeaders()) {
            simpleLineHttpRequestDTO.addHeader(line);
            return;
        }

        if (simpleLineHttpRequestDTO.isParsingBody()) {
            simpleLineHttpRequestDTO.addRequestBody(line);
        }
    }
}
