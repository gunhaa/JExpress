package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import simple.userEntity.Member;
import simple.userEntity.Team;

public class H2Connection implements IDBConnection {

    private static volatile H2Connection INSTANCE;
    private final EntityManagerFactory emf;

    private H2Connection() {
        this.emf = Persistence.createEntityManagerFactory("h2");
    }

    protected static H2Connection getInstance() {
        if (INSTANCE == null) {
            synchronized (H2Connection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new H2Connection();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }

    // 쓰레드마다 한개씩 생성해 로직 실행
    @Override
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
