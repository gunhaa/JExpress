package simple.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import simple.tempEntity.Member;
import simple.tempEntity.Team;

public class H2Connection implements IDBConnection {

    private static volatile H2Connection INSTANCE;
    private final EntityManagerFactory emf;

    private H2Connection() {
        this.emf = Persistence.createEntityManagerFactory("h2");
    }

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
    public EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }

    public void setTestData() {

        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Member member1 = new Member();
            member1.setName("gunha");
            member1.setAge(10);
            member1.setEngName("loopy");
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("insoo");
            member2.setEngName("madcow");
            member2.setAge(20);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("jaewon");
            member3.setEngName("squirrel");
            member3.setAge(30);
            em.persist(member3);

            Member member4 = new Member();
            member4.setName("jihwan");
            member4.setEngName("pai");
            member4.setAge(40);
            em.persist(member4);

            Member member5 = new Member();
            member5.setName("gunha");
            member5.setAge(50);
            member5.setEngName("Roopy");
            em.persist(member5);

            Team team1 = new Team();
            Team team2 = new Team();
            team1.setTeamName("일팀");
            team1.setTeamEngName("1team");
            em.persist(team1);

            team2.setTeamName("이팀");
            team2.setTeamEngName("2team");
            em.persist(team2);

            member1.setTeam(team1);
            member2.setTeam(team1);
            member3.setTeam(team1);
            member4.setTeam(team2);
            member5.setTeam(team2);

            tx.commit();
        }
    }

    // 쓰레드마다 한개씩 생성해 로직 실행
    @Override
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
