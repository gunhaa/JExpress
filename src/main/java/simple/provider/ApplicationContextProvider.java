package simple.provider;

import simple.constant.HttpMethod;
import simple.requestHandler.*;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextProvider {

    private final Map<HttpMethod, RequestHandler> requestHandlers = new HashMap<>();

    private static final ApplicationContextProvider INSTANCE = new ApplicationContextProvider();

}
