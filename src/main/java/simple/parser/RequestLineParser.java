package simple.parser;

import simple.httpRequest.SimpleHttpRequest;
import simple.httpRequest.SimpleHttpRequestDTO;
import simple.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLineParser implements Parser{
    private final SimpleHttpRequestDTO simpleHttpRequestDTO;
    private final Logger logger;
    private final StringBuilder lineBuilder;

    public RequestLineParser(Logger logger) {
        this.simpleHttpRequestDTO = new SimpleHttpRequestDTO();
        this.logger = logger;
        this.lineBuilder = new StringBuilder();
    }

    @Override
    public SimpleHttpRequest parsing(BufferedReader request) throws IOException {

        String line;
        while((line = request.readLine())!=null){
            System.out.println(line);
        }

        return null;
    }
}
