package simple.database;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class MySQLConnection implements IDBConnection {

    private static volatile MySQLConnection INSTANCE;
    private final EntityManagerFactory emf;
    private MySQLConnection() {

        String password = System.getenv("MYSQL_PASS");
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.password", password);

        this.emf = Persistence.createEntityManagerFactory("mysql", props);
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
