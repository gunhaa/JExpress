package simple.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import simple.constant.ServerSettingChecker;
import simple.database.IDBConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static simple.constant.ApplicationSettingFlags.DB_H2;
import static simple.constant.ApplicationSettingFlags.DB_MYSQL;

public class JExpressCRUDRepository implements IJExpressRepository {


    public JExpressCRUDRepository() {}

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        try(EntityManager em = getEntityManager()){
            String clazzName = clazz.getName();

            StringBuilder jpql = new StringBuilder("SELECT m FROM "+ clazzName +" m");
            List<T> resultList = em.createQuery(jpql.toString(), clazz)
                    .getResultList();
            return resultList;
        }
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

    @Override
    public <T> List<T> findListWithNativeQuery(Class<T> clazz, String query){
        try(EntityManager em = getEntityManager()){
            try {
                List resultList = em.createNativeQuery(query, clazz)
                        .getResultList();
                return resultList;
            } catch (NoResultException e) {
                return new ArrayList<>();
            }
        }

    }

    @Override
    public <T> List<T> executeJpql(StringBuilder jpqlQuery, Class<T> mappingClazz, JExpressCondition... conditions){
        try(EntityManager em = getEntityManager()){
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

            System.out.println("query : " +jpqlQuery.toString());
            TypedQuery<T> building = em.createQuery(jpqlQuery.toString(), mappingClazz);

            for (int i = 0; i < conditions.length; i++) {
                String param = paramName(conditions[i].getColumnName(), i);
                building.setParameter(param, conditions[i].getValue());
            }
            return building.getResultList();
        }
    }

    @Override
    public <T> T registerEntityOrNull(Map<String, String> map, Class<T> clazz){
        try(EntityManager em = getEntityManager()){

            T entity;
            EntityTransaction tx = em.getTransaction();

            try {
                ObjectMapper mapper = new ObjectMapper();
                entity = mapper.convertValue(map, clazz);

                tx.begin();
                em.persist(entity);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                return null;
            }
            return entity;
        }
    }

    private String paramName(String columnName, int index) {
        return columnName.replace(".", "") + "_" + index;
    }

    protected static EntityManager getEntityManager() {
        IDBConnection db;

        if(ServerSettingChecker.isServerEnabled(DB_MYSQL)){
            db = IDBConnection.getMySQLConnection();
            return db.getEntityManager();
        }

        if(ServerSettingChecker.isServerEnabled(DB_H2)){
            db = IDBConnection.getH2Connection();
            return db.getEntityManager();
        }

        // default used h2 db
        return IDBConnection.getH2Connection().getEntityManager();
    }
}
