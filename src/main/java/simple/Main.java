package simple;

import simple.repository.CustomRepository;
import simple.repository.JExpressCRUDRepository;
import simple.repository.JExpressCondition;
import simple.server.IServer;
import simple.server.JExpress;
import simple.userEntity.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static simple.constant.ApplicationSettingFlags.API_DOCS;
import static simple.constant.ApplicationSettingFlags.CORS;
import static simple.constant.ApplicationSettingFlags.RESPONSE_TIME;
import static simple.constant.ApplicationSettingFlags.DB_H2;
import static simple.constant.ApplicationSettingFlags.DB_MYSQL;

public class Main {
    public static void main(String[] args) throws IOException{

        //        IServer app = new JExpress();
        // threadPool을 이용한 서버 생성방법
        IServer app = new JExpress(15);

        app.use(API_DOCS);
        app.use(CORS);
//        app.use(CORS, "https://bitlibrary.com");
        app.use(RESPONSE_TIME);
        app.use(DB_H2);
//        app.use(DB_MYSQL);

        //curl -i -X GET "localhost:8020/members"
        app.get("/members", (req, res) -> {
            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            List<Member> List = jcr.findAll(Member.class);

            res.send(List);
        }, Member.class);

        //curl -i -X GET "localhost:8020/teams"
        app.get("/teams", (req, res) -> {
            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            List<Team> List = jcr.findAll(Team.class);

            res.send(List);
        }, Team.class);

        // curl -i -X GET "localhost:8020/member/team?teamName=일팀"
        app.get("/member/team", (req, res) -> {

            String key1 = "teamName";
            String value1 = req.getQueryString(key1);

            StringBuilder query = new StringBuilder("SELECT m.name, m.engName, m.age FROM MEMBER m JOIN TEAM t ON t.TEAM_ID=m.TEAM_ID WHERE 1=1");
            // ' 를 구분하는 validation 함수 필요
            if(value1 != null){
                query.append(" AND ").append(key1).append("=").append("'").append(value1).append("'");
            }
            JExpressCRUDRepository jcr = new JExpressCRUDRepository();

            List<MemberDto1> List = jcr.findListWithNativeQuery(MemberDto1.class, query.toString());

            res.send(List);
        }, MemberDto1.class);

        // curl -i -X GET "localhost:8020/member/team/gunha/1"
        // success
        app.get("/member/team/:memberName/:teamId", (req, res) -> {

            String memberName = req.getParam("memberName");
            JExpressCondition condition1 = new JExpressCondition("m.name", memberName);

            String teamId = req.getParam("teamId");
            JExpressCondition condition2 = new JExpressCondition("t.id", teamId);

            StringBuilder jpql = new StringBuilder("SELECT new simple.userEntity.MemberDto2(m.name, m.engName, m.team) FROM Member m join m.team t");

            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            List<MemberDto2> result = jcr.executeJpql(jpql, MemberDto2.class, condition1, condition2);

            res.send(result);

        }, MemberDto2.class);

        // curl -i -X GET "localhost:8020/member/team/gunha"
        app.get("/member/team/:memberName", (req, res)->{

            String memberName = req.getParam("memberName");
            JExpressCondition condition = new JExpressCondition("m.name", memberName);

            StringBuilder jpql = new StringBuilder("SELECT new simple.userEntity.MemberDto3(m.age, m.engName) FROM Member m");

            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            List<MemberDto3> result = jcr.executeJpql(jpql, MemberDto3.class, condition);

            res.send(result);

        }, MemberDto3.class);
        
        /* body
        {
          "name": "테스트",
          "age": "150",
          "engName" : "Test"
        }
        */
        // member 등록
        app.post("/member", (req, res)-> {

            Map<String, String> map = req.getBodyMap();

            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            Member registerMember = jcr.registerEntityOrNull(map, Member.class);

            res.send(registerMember);

        }, Member.class);


        /* body
        {
          "teamName": "테스트팀",
          "teamEngName": "testTeam",
        }
        */
        // team 등록
        app.post("/team", (req, res)-> {
            Map<String, String> map = req.getBodyMap();

            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            Team registerTeam = jcr.registerEntityOrNull(map, Team.class);

            res.send(registerTeam);
        }, Team.class);


        /* body
        {
          "name": "재원",
          "age": "120",
          "engName": "jae",
          "teamId": "1"
        }
        */
        // member team 동시 등록
        app.post("/member/team", (req, res)-> {
            Map<String, String> map = req.getBodyMap();

            Member registerMember = CustomRepository.registerMemberWithTeamOrNull(map);

            res.send(registerMember);
        }, MemberTeamDto.class);

        String port = System.getenv("PORT");
        // use docker
        //app.run(Integer.parseInt(port));
        app.run("8020");
    }
}