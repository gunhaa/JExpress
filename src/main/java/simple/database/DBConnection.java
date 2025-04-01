package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public interface DBConnection {
    EntityManagerFactory getEntityManagerFactory();
    EntityManager getEntityManager();
}
