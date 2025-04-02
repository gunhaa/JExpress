package simple.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import simple.constant.ServerSettingChecker;
import simple.database.DBConnection;

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

        StringBuilder jpql = new StringBuilder("SELECT m FROM "+ clazzName +" m");
        List<T> resultList = em.createQuery(jpql.toString(), clazz)
                .getResultList();

        return resultList;
    }

    /**
     * JExpressQueryString이 전달되지 않으면 entity의 첫 결과를 반환하며, 결과가 여러개라면 첫 번째 결과를 반환함
     */
    public <T> T findSingleTableEntity(Class<T> clazz, JExpressQueryString... conditions){
        DBConnection dbConnection = selectDb();
        EntityManager em = dbConnection.getEntityManager();
        String clazzName = clazz.getName();
        StringBuilder jpql = new StringBuilder("SELECT m FROM "+ clazzName +" m  WHERE 1=1");

        TypedQuery<T> query = em.createQuery(jpql.toString(), clazz);

        if(conditions == null){
            List<T> resultList = query
                    .getResultList();
            return resultList.get(0);
        } else {


            for (JExpressQueryString condition : conditions) {
                String key = condition.getKey();
                jpql.append(" AND m.").append(key).append("=:").append(key);
            }

            query = em.createQuery(jpql.toString(), clazz);

            for (JExpressQueryString condition : conditions) {
                String key = condition.getKey();
                String value = condition.getValue();
                query.setParameter(key, value);
            }

            List<T> resultList = query
                    .getResultList();

            if(resultList.isEmpty()){
                return null;
            }

            return resultList.get(0);
        }
    }

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
