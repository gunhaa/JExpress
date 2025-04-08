package simple.database;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MySQLConnection implements IDBConnection {

    private static volatile MySQLConnection INSTANCE;
    private final EntityManagerFactory emf;
    private MySQLConnection() {
        this.emf = Persistence.createEntityManagerFactory("mysql");
    }

    protected static MySQLConnection getInstance() {
        if (INSTANCE == null) {
            synchronized (MySQLConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MySQLConnection();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
