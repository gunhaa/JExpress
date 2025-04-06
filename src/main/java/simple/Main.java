package simple;

import simple.repository.JExpressCRUDRepository;
import simple.repository.JExpressCondition;
import simple.server.IServer;
import simple.server.JExpress;
import simple.tempEntity.Member;
import simple.tempEntity.MemberTestDTO1;
import simple.tempEntity.MemberTestDTO2;
import simple.tempEntity.MemberTestDTO3;

import java.io.IOException;
import java.util.List;

import static simple.constant.ApplicationSettingFlags.*;

public class Main {
    public static void main(String[] args) throws IOException{
        IServer app = new JExpress();
        // threadPool을 이용한 서버 생성방법
        // Server app = new JExpress(15);

        app.use(API_DOCS);
        app.use(CORS);
//        app.use(CORS, "https://bitlibrary.com");
        app.use(RESPONSE_TIME);
        app.use(DB_H2);
//        app.use(DB_MYSQL);
//        app.use(GET_CACHE);


        // test url = localhost:8020/member?name=gunha&age=50
        // issue : 반드시 파라미터가 두개 다 있어야함
//        app.get("/member" , (req, res) -> {
//            String key1 = "name";
//            String value1 = req.getQueryString(key1);
//            JExpressCondition jqs1 = new JExpressCondition(key1, value1);
//
//            String key2 = "age";
//            String value2 = req.getQueryString(key2);
//            JExpressCondition jqs2 = new JExpressCondition(key2, value2);
//
//            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
//            MemberTestDTO1 findMember = jcr.findEntity(MemberTestDTO1.class, jqs1, jqs2);
//
//            res.send(findMember);
//        }, MemberTestDTO1.class);

        //curl -i -X GET "localhost:8020/members"
        app.get("/members", (req, res) -> {
            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            List<?> List = jcr.findAll(Member.class);

            res.send(List);
        }, List.class);

        // curl -i -X GET "localhost:8020/member/team?teamName=일팀"
        app.get("/member/team", (req, res) -> {

            String key1 = "teamName";
            String value1 = req.getQueryString(key1);

            StringBuilder query = new StringBuilder("SELECT m.name, m.engName, m.age FROM MEMBER m JOIN TEAM t ON t.TEAM_ID=m.TEAM_ID WHERE 1=1");
            // ' 를 구분하는 validation 함수 필요
            if(value1 != null){
                query.append(" AND ").append(key1).append("=").append("'").append(value1).append("'");
            }
            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();

            List<MemberTestDTO1> List = jcr.findListWithNativeQuery(MemberTestDTO1.class, query.toString());

            res.send(List);
        }, MemberTestDTO1.class);

        // curl -i -X GET "localhost:8020/member/team/gunha/1"
        // success
        app.get("/member/team/:memberName/:teamId", (req, res) -> {

            String memberName = req.getParam("memberName");
            JExpressCondition condition1 = new JExpressCondition("m.name", memberName);

            String teamId = req.getParam("teamId");
            JExpressCondition condition2 = new JExpressCondition("t.id", teamId);

            StringBuilder jpql = new StringBuilder("SELECT new simple.tempEntity.MemberTestDTO2(m.name, m.engName, m.team) FROM Member m join m.team t");

            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            List<MemberTestDTO2> result = jcr.executeJpql(jpql, MemberTestDTO2.class, condition1, condition2);

            res.send(result);

        }, MemberTestDTO2.class);

        // curl -i -X GET "localhost:8020/member/team/gunha"
        // success
        app.get("/member/team/:memberName", (req, res)->{

            String memberName = req.getParam("memberName");
            JExpressCondition condition = new JExpressCondition("m.name", memberName);

            StringBuilder jpql = new StringBuilder("SELECT new simple.tempEntity.MemberTestDTO3(m.age, m.engName) FROM Member m");

            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            List<MemberTestDTO3> result = jcr.executeJpql(jpql, MemberTestDTO3.class, condition);

            res.send(result);

        }, MemberTestDTO3.class);

        app.post("/member", (req, res)-> {
            System.out.println("post 요청 성공");
            class Test{
                String msg;
                public Test(String m){
                     this.msg = m;
                }
            }

            res.send(new Test("test"));
        });

        app.run(8020);
    }
}