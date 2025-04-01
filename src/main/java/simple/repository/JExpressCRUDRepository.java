package simple.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.database.DBConnection;
import simple.database.H2Connection;
import simple.httpRequest.ErrorStatus;

import java.util.List;

import static simple.constant.ApplicationSetting.DB_H2;
import static simple.constant.ApplicationSetting.DB_MYSQL;

public class JExpressCRUDRepository implements JExpressRepository {

    private static volatile JExpressCRUDRepository INSTANCE;

    private JExpressCRUDRepository() {}

    public static JExpressCRUDRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (JExpressCRUDRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JExpressCRUDRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        DBConnection dbConnection = selectDb();
        EntityManager em = dbConnection.getEntityManager();

        String clazzName = clazz.getName();
        String jpql = "SELECT m FROM "+ clazzName +" m";
        List<T> resultList = em.createQuery(jpql, clazz)
                .getResultList();

        return resultList;
    }

//    public void findEntity(Class<?> clazz, String... conditions){
//        DBConnection dbConnection = selectDb();
//        if(dbConnection == null){
//            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.INTERNAL_SERVER_ERROR_500, "plz setting db"));
//        }
//
//        EntityManager em = dbConnection.getEntityManager();
//
//        String clazzName = clazz.getName();
//    }

    private DBConnection selectDb() {
        DBConnection db;

        if(ServerSettingChecker.isServerEnabled(DB_MYSQL)){
            db = DBConnection.getMySQLConnectionInstance();
            return db;
        }

        if(ServerSettingChecker.isServerEnabled(DB_H2)){
            db = DBConnection.getH2ConnectionInstance();
            return db;
        }

        // default used h2 db
        return DBConnection.getH2ConnectionInstance();
    }
}
