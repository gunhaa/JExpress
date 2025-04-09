package simple.parser;

import simple.httpRequest.HttpRequest;
import simple.httpRequest.CharHttpRequestBuilder;
import simple.logger.ILogger;
import simple.logger.LoggerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequestCharParser implements IHttpRequestParser {
    private final CharHttpRequestBuilder charHttpRequestBuilder;
    private final LoggerManager loggerManager;
    private final StringBuilder lineBuilder;

    public HttpRequestCharParser(LoggerManager loggerManager) {
        this.charHttpRequestBuilder = new CharHttpRequestBuilder();
        this.loggerManager = loggerManager;
        this.lineBuilder = new StringBuilder();
    }

    @Override
    public HttpRequest parsing(BufferedReader request) throws IOException {
        int ch;

        while ( charHttpRequestBuilder.getContentLength() != 0
                && (ch = request.read()) != -1) {

            if(charHttpRequestBuilder.isParsingBody()){
                charHttpRequestBuilder.addRequestBody((char)ch);
                continue;
            }

            if(ch == '\r'){
                continue;
            }

            if(ch == '\n'){

                if(lineBuilder.isEmpty()){
                    charHttpRequestBuilder.updateRemainingBodyLength();
                } else {
                    processHttpRequest(lineBuilder);
                }
                lineBuilder.setLength(0);

            } else {
                lineBuilder.append((char) ch);
            }
        }

        if (charHttpRequestBuilder.isParsingBody() && !lineBuilder.isEmpty()) {
            charHttpRequestBuilder.addRequestBody(lineBuilder.toString());
        }

        if(!charHttpRequestBuilder.getBody().isEmpty()){
            charHttpRequestBuilder.parsingJsonToMap(charHttpRequestBuilder.getBody());
        }

        loggerManager.addLog(charHttpRequestBuilder.getLogBuffer());
        return HttpRequest.builder()
                .method(charHttpRequestBuilder.getMethod())
                .url(charHttpRequestBuilder.getUrl())
                .params(new HashMap<>())
                .protocol(charHttpRequestBuilder.getProtocol())
                .queryString(charHttpRequestBuilder.getQueryString())
                .header(charHttpRequestBuilder.getHeader())
                .bodyMap(charHttpRequestBuilder.getBodyMap())
                .errorQueue(charHttpRequestBuilder.getErrorQueue())
                .build();
    }

    private void processHttpRequest(StringBuilder line) {
        if (charHttpRequestBuilder.isRequestLineParsed()) {
            charHttpRequestBuilder.addRequestLine(line.toString());
            return;
        }

        if (charHttpRequestBuilder.isParsingHeaders()) {
            charHttpRequestBuilder.addHeader(line.toString());
            return;
        }

        if (charHttpRequestBuilder.isParsingBody()) {
            charHttpRequestBuilder.addRequestBody(line.toString());
        }
    }

    @Deprecated
    private void processHttpBody(char c){
        charHttpRequestBuilder.addRequestBody(c);
    }

}
