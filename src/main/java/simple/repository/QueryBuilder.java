package simple.repository;

public class QueryBuilder {

    private StringBuilder query;


    // method chaining
    // stdin SELECT m.name, m.engName, t.teamName FROM MEMBER m JOIN TEAM t ON t.TEAM_ID=m.TEAM_ID WHERE 1=1 AND
    public StringBuilder select(String select, Class<?> clazz){

        return null;
    }
}
