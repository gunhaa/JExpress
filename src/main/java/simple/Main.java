package simple;

import simple.httpRequest.ErrorStatus;
import simple.repository.CustomRepository;
import simple.repository.JExpressCRUDRepository;
import simple.repository.JExpressCondition;
import simple.server.IServer;
import simple.server.JExpress;
import simple.userEntity.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static simple.constant.ApplicationSettingFlags.*;
import static simple.constant.HttpStatus.BAD_REQUEST_400;

public class Main {
    public static void main(String[] args) throws IOException{

        //        IServer app = new JExpress();
        // threadPool을 이용한 서버 생성방법
        IServer app = new JExpress(15);

        app.use(API_DOCS);
        app.use(CORS);
//        app.use(CORS, "https://bitlibrary.com");
        app.use(REQUEST_LOGGER);
        app.use(RESPONSE_TIME);
        app.use(DB_H2);
//        app.use(DB_MYSQL);

        //curl -i -X GET "localhost:8123/members"
        app.get("/members", (req, res) -> {
            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            List<Member> List = jcr.findAll(Member.class);

            res.send(List);
        }, Member.class);

        //curl -i -X GET "localhost:8123/teams"
        app.get("/teams", (req, res) -> {
            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            List<Team> List = jcr.findAll(Team.class);

            res.send(List);
        }, Team.class);

        // curl -i -X GET "localhost:8123/member/team?teamName=일팀"
        // curl -G "localhost:8123/member/team" --data-urlencode "teamName=drop table users"
        app.get("/member/team", (req, res) -> {

            String key1 = "teamName";
            String value1 = req.getQueryString(key1);
            JExpressCRUDRepository jcr = new JExpressCRUDRepository();

            if(value1 != null && jcr.isSqlInjection(value1)){
                res.send(new ErrorStatus(BAD_REQUEST_400, "sql injection detected"));
                return;
            }

            StringBuilder query = new StringBuilder("SELECT m.name, m.engName, m.age FROM MEMBER m JOIN TEAM t ON t.TEAM_ID=m.TEAM_ID WHERE 1=1");
            if(value1 != null){
                query.append(" AND ").append(key1).append("=").append("'").append(value1).append("'");
            }

            List<MemberDto1> List = jcr.findListWithNativeQuery(MemberDto1.class, query.toString());

            res.send(List);
        }, MemberDto1.class);

        // curl -i -X GET "localhost:8123/member/team/gunha/1"
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

        // curl -i -X GET "localhost:8123/member/team/gunha"
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

            Map<String, String> body = req.getBodyMap();

            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            Member registerMember = jcr.registerEntityOrNull(body, Member.class);

            res.send(registerMember);

        }, Member.class);


        /* body
        {
          "teamName": "테스트팀",
          "teamEngName": "testTeam"
        }
        */
        // team 등록
        app.post("/team", (req, res)-> {
            Map<String, String> body = req.getBodyMap();

            JExpressCRUDRepository jcr = new JExpressCRUDRepository();
            Team registerTeam = jcr.registerEntityOrNull(body, Team.class);

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
        // team에 소속된 member 등록
        app.post("/member/team", (req, res)-> {
            Map<String, String> body = req.getBodyMap();

            Member registerMember = CustomRepository.registerMemberWithTeamOrNull(body);

            res.send(registerMember);
        }, MemberTeamDto.class);

        String port = System.getenv("PORT");
        app.run(port);
    }
}