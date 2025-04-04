package simple.parser;

import simple.httpRequest.HttpRequest;
import simple.httpRequest.SimpleLineHttpRequestDTO;
import simple.logger.ILogger;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestLineParser implements IHttpRequestParser {
    private final SimpleLineHttpRequestDTO simpleLineHttpRequestDTO;
    private final ILogger ILogger;

    public HttpRequestLineParser(ILogger ILogger) {
        this.simpleLineHttpRequestDTO = new SimpleLineHttpRequestDTO();
        this.ILogger = ILogger;
    }

    @Override
    public HttpRequest parsing(BufferedReader request) throws IOException {

        String line;
        while((line = request.readLine())!=null){
            ILogger.add(line);
            processRequestLine(line);
            if(simpleLineHttpRequestDTO.isParsingBody() && simpleLineHttpRequestDTO.isFinishBodyParsing()){
                StringBuilder requestBody = simpleLineHttpRequestDTO.getBody();
                simpleLineHttpRequestDTO.parsingJsonToMap(requestBody);
                break;
            }

        }

        return HttpRequest.builder()
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
