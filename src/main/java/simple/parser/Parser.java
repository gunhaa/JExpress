package simple.parser;

import simple.httpRequest.SimpleHttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

public interface Parser {
    SimpleHttpRequest parsing(BufferedReader request) throws IOException;
}
