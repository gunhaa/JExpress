package simple.parser;

import simple.httpRequest.HttpRequest;
import simple.httpRequest.CharHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestCharacterParser implements Parser{
    private final CharHttpRequestDTO charHttpRequestDTO;
    private final Logger logger;
    private final StringBuilder lineBuilder;

    public RequestCharacterParser(Logger logger) {
        this.charHttpRequestDTO = new CharHttpRequestDTO();
        this.logger = logger;
        this.lineBuilder = new StringBuilder();
    }

    @Override
    public HttpRequest parsing(BufferedReader request) throws IOException {
        int ch;

        while ( charHttpRequestDTO.getContentLength() != 0
                && (ch = request.read()) != -1) {

            logger.add((char) ch);

            if(charHttpRequestDTO.isParsingBody()){
//                processHttpBody((char)ch);
                charHttpRequestDTO.addRequestBody((char)ch);
                continue;
            }

            if(ch == '\r'){
                continue;
            }

            if(ch == '\n'){

                if(lineBuilder.isEmpty()){
                    charHttpRequestDTO.updateRemainingBodyLength();
                } else {
                    processHttpRequest(lineBuilder);
                }
                lineBuilder.setLength(0);

            } else {
                lineBuilder.append((char) ch);
            }
        }

        if (charHttpRequestDTO.isParsingBody() && !lineBuilder.isEmpty()) {
            charHttpRequestDTO.addRequestBody(lineBuilder.toString());
        }

        if(!charHttpRequestDTO.getBody().isEmpty()){
            charHttpRequestDTO.parsingJsonToMap(charHttpRequestDTO.getBody());
        }

        return HttpRequest.builder()
                .method(charHttpRequestDTO.getMethod())
                .url(charHttpRequestDTO.getUrl())
                .protocol(charHttpRequestDTO.getProtocol())
                .queryString(charHttpRequestDTO.getQueryString())
                .header(charHttpRequestDTO.getHeader())
                .bodyMap(charHttpRequestDTO.getBodyMap())
                .errorQueue(charHttpRequestDTO.getErrorQueue())
                .build();
    }

    private void processHttpRequest(StringBuilder line) {
        if (charHttpRequestDTO.isRequestLineParsed()) {
            charHttpRequestDTO.addRequestLine(line.toString());
            return;
        }

        if (charHttpRequestDTO.isParsingHeaders()) {
            charHttpRequestDTO.addHeader(line.toString());
            return;
        }

        if (charHttpRequestDTO.isParsingBody()) {
            charHttpRequestDTO.addRequestBody(line.toString());
        }
    }

    @Deprecated
    private void processHttpBody(char c){
        charHttpRequestDTO.addRequestBody(c);
    }

}
