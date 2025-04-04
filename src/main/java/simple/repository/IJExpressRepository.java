package simple.repository;

import java.util.List;

public interface IJExpressRepository {
    <T> List<T> findAll(Class<T> clazz);
}
