package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import simple.middleware.ApiDocs;
import simple.tempEntity.Member;

import java.util.HashMap;
import java.util.Map;

public class H2Connection implements DBConnection{

    private static volatile H2Connection INSTANCE;

    private H2Connection() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static H2Connection getInstance() {
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
    public void getConnection(){
        // EntityManagerFactory 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
        EntityManager em = emf.createEntityManager();

        try {
            // 트랜잭션 시작
            em.getTransaction().begin();

            // 새로운 Member 저장
            Member member = new Member();
            member.setName("gg");
            member.setAge(11);
            em.persist(member);

            // 트랜잭션 커밋
            em.getTransaction().commit();

            // Member 조회
            Member foundMember = em.find(Member.class, member.getId());
            System.out.println("조회된 Member: " + foundMember);

        } finally {
            em.close(); // EntityManager 닫기
            emf.close(); // EntityManagerFactory 닫기
        }
    }

    public void getTestDataset() {

    }
}
