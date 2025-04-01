package simple.repository;

import java.util.List;

public interface JExpressRepository {
    <T> List<T> findAll(Class<T> clazz);
}
