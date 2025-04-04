package simple.url;

import simple.httpResponse.LambdaHandlerWrapper;

public interface ITrie {
    void insert(String path, LambdaHandlerWrapper handler);

}
