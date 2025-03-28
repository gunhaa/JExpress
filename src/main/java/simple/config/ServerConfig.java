package simple.config;

import simple.context.ApplicationConfig;

@Deprecated
public class ServerConfig {

    private boolean isSingleThread;
    private int threadPool;
    private static final ServerConfig INSTANCE = new ServerConfig(ApplicationConfig.getInstance());

    private ServerConfig(ApplicationConfig applicationConfig) {
        loadConfig(applicationConfig);
    }

    private void loadConfig(ApplicationConfig applicationConfig) {

    }

    public static ServerConfig getInstance(){
        return INSTANCE;
    }

}
