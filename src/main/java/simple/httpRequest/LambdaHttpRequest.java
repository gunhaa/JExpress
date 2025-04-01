package simple.httpRequest;

import jakarta.persistence.EntityManager;
import simple.constant.HttpStatus;
import simple.constant.ServerSettingChecker;
import simple.database.DBConnection;

import java.util.HashMap;
import java.util.List;

import static simple.constant.ApplicationSetting.*;

public class LambdaHttpRequest {

    private final HttpRequest httpRequest;

    public LambdaHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getQueryString(String target){
        HashMap<String, String> qs = httpRequest.getQueryString();
        String value = qs.get(target);
        if(value == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.BAD_REQUEST_400, "unvalid QueryString"));
        }
        return value;
    }

    public void findEntity(Class<?> clazz, String... conditions){
        DBConnection dbConnection = selectDb();
        if(dbConnection == null){
            httpRequest.getErrorQueue().add(new ErrorStatus(HttpStatus.INTERNAL_SERVER_ERROR_500, "plz setting db"));
        }

        EntityManager em = dbConnection.getEntityManager();

        String clazzName = clazz.getName();
    }

    public List<?> findAll(Class<?> clazz){
        DBConnection dbConnection = selectDb();
        EntityManager em = dbConnection.getEntityManager();

        String clazzName = clazz.getName();
        String jpql = "SELECT m FROM "+ clazzName +" m";
        List<?> resultList = em.createQuery(jpql, clazz)
                .getResultList();

        return resultList;
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

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
