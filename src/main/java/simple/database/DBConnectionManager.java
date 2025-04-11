package simple.database;

import jakarta.persistence.EntityManager;

public class DBConnectionManager {

    private static volatile DBConnectionManager INSTANCE;
    private final IDBConnection idbConnection;

    private DBConnectionManager() {
        this.idbConnection = DBConnectionStrategyFactory.create();
    }

    public static DBConnectionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DBConnectionManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBConnectionManager();
                }
            }
        }
        return INSTANCE;
    }

    public EntityManager getConfigureEntityManager() {
        return idbConnection.getEntityManager();
    }
}
