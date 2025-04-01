package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public interface DBConnection {
    EntityManagerFactory getEntityManagerFactory();
    EntityManager getEntityManager();

    static DBConnection getH2ConnectionInstance() {
        return H2Connection.getInstance();
    }

    static DBConnection getMySQLConnectionInstance() {
        return MySQLConnection.getInstance();
    }
}
