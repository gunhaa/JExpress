package simple.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import simple.constant.ServerSettingChecker;
import simple.database.IDBConnection;

import java.util.ArrayList;
import java.util.List;

import static simple.constant.ApplicationSettingFlags.DB_H2;
import static simple.constant.ApplicationSettingFlags.DB_MYSQL;

public class JExpressCRUDRepository implements IJExpressRepository {

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
        EntityManager em = getEntityManager();

        String clazzName = clazz.getName();

        StringBuilder jpql = new StringBuilder("SELECT m FROM "+ clazzName +" m");
        List<T> resultList = em.createQuery(jpql.toString(), clazz)
                .getResultList();

        return resultList;
    }

    /**
     * JExpressQueryString이 전달되지 않으면 entity의 첫 결과를 반환하며, 결과가 여러개라면 첫 번째 결과를 반환함
     * 연관 객체가 있을 시 순환 참조 문제 있음
     */
    @Deprecated
    public <T> T findEntity(Class<T> clazz, JExpressCondition... conditions){
        EntityManager em = getEntityManager();

        String clazzName = clazz.getName();
        StringBuilder jpql = new StringBuilder("SELECT new simple.tempEntity.MemberTestDTO1(m.name, m.engName, m.team, m.age) FROM Member m WHERE 1=1");

        TypedQuery<T> query = em.createQuery(jpql.toString(), clazz);

        // todo : refactoring
        if(conditions.length == 0){
            List<T> resultList = query
                    .getResultList();
            return resultList.get(0);
        } else {

            for (JExpressCondition condition : conditions) {
                String key = condition.getColumnName();
                jpql.append(" AND m.").append(key).append("=:").append(key);
            }

            query = em.createQuery(jpql.toString(), clazz);

            for (JExpressCondition condition : conditions) {
                String key = condition.getColumnName();
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

    public <T> List<T> findListWithNativeQuery(Class<T> clazz, String query){
        EntityManager em = getEntityManager();

        try {
            List resultList = em.createNativeQuery(query, clazz)
                    .getResultList();
            return resultList;
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public <T> List<T> executeJpql(StringBuilder jpqlQuery, Class<T> mappingClazz, JExpressCondition... conditions){
        EntityManager em = getEntityManager();

        String jpqlLower = jpqlQuery.toString().toLowerCase();
        boolean hasWhere = jpqlLower.contains("where");

        if (conditions.length != 0) {
            for (int i = 0; i < conditions.length; i++) {
                JExpressCondition cond = conditions[i];

                if (i == 0 && !hasWhere) {
                    jpqlQuery.append(" WHERE ");
                } else {
                    jpqlQuery.append(" AND ");
                }

                jpqlQuery.append(cond.getColumnName())
                        .append("  = :")
                        .append(paramName(cond.getColumnName(), i));
            }
        }

        TypedQuery<T> building = em.createQuery(jpqlQuery.toString(), mappingClazz);

        for (int i = 0; i < conditions.length; i++) {
            String param = paramName(conditions[i].getColumnName(), i);
            building.setParameter(param, conditions[i].getValue());
        }

        return building.getResultList();
    }

    private String paramName(String columnName, int index) {
        return columnName.replace(".", "") + "_" + index;
    }


    private EntityManager getEntityManager() {
        IDBConnection db;

        if(ServerSettingChecker.isServerEnabled(DB_MYSQL)){
            db = IDBConnection.getMySQLConnectionInstance();
            return db.getEntityManager();
        }

        if(ServerSettingChecker.isServerEnabled(DB_H2)){
            db = IDBConnection.getH2ConnectionInstance();
            return db.getEntityManager();
        }

        // default used h2 db
        return IDBConnection.getH2ConnectionInstance().getEntityManager();
    }
}
