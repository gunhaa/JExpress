package simple.repository;

import java.util.List;
import java.util.Map;

public interface IJExpressRepository {
    <T> List<T> findAll(Class<T> clazz);
    <T> List<T> findListWithNativeQuery(Class<T> clazz, String query);
    <T> List<T> executeJpql(StringBuilder jpqlQuery, Class<T> mappingClazz, JExpressCondition... conditions);
    <T> T registerEntityOrNull(Map<String, String> map, Class<T> clazz);
}
