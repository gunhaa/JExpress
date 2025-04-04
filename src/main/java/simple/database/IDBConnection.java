package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public interface IDBConnection {
    EntityManagerFactory getEntityManagerFactory();
    EntityManager getEntityManager();

    static IDBConnection getH2ConnectionInstance() {
        return H2Connection.getInstance();
    }

    static IDBConnection getMySQLConnectionInstance() {
        return MySQLConnection.getInstance();
    }
}
