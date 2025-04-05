package simple;

import simple.repository.JExpressCRUDRepository;
import simple.repository.JExpressCondition;
import simple.server.IServer;
import simple.server.JExpress;
import simple.tempEntity.Member;
import simple.tempEntity.MemberDTO1;
import simple.tempEntity.MemberDTO2;

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
        app.get("/member" , (req, res) -> {
            // 반드시 파라미터가 두개 다 있어야하는 문제가 있다
            String key1 = "name";
            String value1 = req.getQueryString(key1);
            JExpressCondition jqs1 = new JExpressCondition(key1, value1);

            String key2 = "age";
            String value2 = req.getQueryString(key2);
            JExpressCondition jqs2 = new JExpressCondition(key2, value2);

            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            MemberDTO1 findMember = jcr.findEntity(MemberDTO1.class, jqs1, jqs2);

            res.send(findMember);
//            res.send(findMember, Member.class);
        }, MemberDTO1.class);

        // test url = localhost:8020/members
        app.get("/members", (req, res) -> {
            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            List<?> List = jcr.findAll(Member.class);

            res.send(List);
//            res.send(List, List.class);
        }, List.class);

        // test url = localhost:8020/member/team?teamName=일팀
        app.get("/member/team", (req, res) -> {

            String key1 = "teamName";
            String value1 = req.getQueryString(key1);

            StringBuilder query = new StringBuilder("SELECT m.name, m.engName, t.teamName FROM MEMBER m JOIN TEAM t ON t.TEAM_ID=m.TEAM_ID WHERE 1=1 AND ");
            // ' 를 구분하는 validation 함수 필요

            query.append(key1).append("=").append("'").append(value1).append("'");
            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();

            List<MemberDTO2> List = jcr.findListWithNativeQuery(MemberDTO2.class, query.toString());

            res.send(List);
//            res.send(List, List.class);
        }, List.class);

        // curl -i -X GET "localhost:8020/member/team/gunha/1"
        // success
        app.get("/member/team/:memberName/:teamId", (req, res) -> {

            StringBuilder jpql = new StringBuilder("SELECT new simple.tempEntity.MemberDTO2(m.name, m.engName, m.team) FROM Member m join m.team t");
            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();

            String memberName = req.getParam("memberName");
            System.out.println("lambda memberId : " + memberName);
            JExpressCondition condition1 = new JExpressCondition("m.name", memberName);

            String teamId = req.getParam("teamId");
            System.out.println("lambda teamId : " + teamId);
            JExpressCondition condition2 = new JExpressCondition("t.id", teamId);

            List<MemberDTO2> result = jcr.findListWithJpql(jpql, MemberDTO2.class, condition1, condition2);

            res.send(result);

        }, MemberDTO2.class);

        app.run(8020);
    }
}