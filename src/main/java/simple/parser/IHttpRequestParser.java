package simple.parser;

import simple.httpRequest.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

public interface IHttpRequestParser {
    HttpRequest parsing(BufferedReader request) throws IOException;
}
