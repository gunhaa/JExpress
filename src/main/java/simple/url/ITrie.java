package simple.url;

import simple.httpResponse.ILambdaHandler;

public interface ITrie {
    public void insert(String path, ILambdaHandler handler);
}
