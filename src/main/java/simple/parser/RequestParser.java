package simple.parser;

import com.google.gson.Gson;
import simple.httpRequest.HttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {
    private HttpRequestDTO httpRequestDTO;
    private Logger logger;
    private StringBuilder lineBuilder;

    public RequestParser(HttpRequestDTO httpRequestDTO, Logger logger) {
        this.httpRequestDTO = httpRequestDTO;
        this.logger = logger;
        this.lineBuilder = new StringBuilder();
    }

    public void parsing(BufferedReader request) throws IOException {
        int ch;
        String line;

        while ((ch = request.read()) != -1) {
            logger.add((char) ch);

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
