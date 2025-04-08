package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public interface IDBConnection {
    EntityManagerFactory getEntityManagerFactory();
    EntityManager getEntityManager();

    static IDBConnection getH2Connection() {
        return H2Connection.getInstance();
    }

    static IDBConnection getMySQLConnection() {
        return MySQLConnection.getInstance();
    }
}
