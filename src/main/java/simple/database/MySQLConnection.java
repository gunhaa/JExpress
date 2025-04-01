package simple.database;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// Todo
public class MySQLConnection implements DBConnection{
    @Override
    public EntityManagerFactory getEntityManagerFactory() {
//        Configuration configuration = new Configuration();
//        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/testdb");
//        configuration.setProperty("hibernate.connection.username", "root");
//        configuration.setProperty("hibernate.connection.password", "password");
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//
//        // SessionFactory 생성
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        try {
//            // Connection 가져오기
//            Connection connection = session.doReturningWork(Connection::unwrap);
//            System.out.println("DB 연결 성공: " + connection.getMetaData().getURL());
//
//            // Connection 닫기
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            session.close();
//            sessionFactory.close();
//        }
        return null;
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }
}
