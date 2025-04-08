# JExpress

- json을 반환하는 REST API를 만들수 있는 웹 프레임워크

## 사용예제

```java

public class Main {
  public static void main(String[] args) throws IOException{

    // threadPool을 이용한 서버 생성
    IServer app = new JExpress(15);

    app.use(API_DOCS);
    app.use(CORS, "https://bitlibrary.com");
    app.use(RESPONSE_TIME);
    app.use(DB_MYSQL);

    //curl -i -X GET "localhost:8020/members"
    app.get("/members", (req, res) -> {
      JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
      List<Member> List = jcr.findAll(Member.class);

      res.send(List);
    }, Member.class);

    //curl -i -X GET "localhost:8020/teams"
    app.get("/teams", (req, res) -> {
      JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
      List<Team> List = jcr.findAll(Team.class);

      res.send(List);
    }, Team.class);
    
    // curl -i -X GET "localhost:8020/member/team/gunha/1"
    // success
    app.get("/member/team/:memberName/:teamId", (req, res) -> {

      String memberName = req.getParam("memberName");
      JExpressCondition condition1 = new JExpressCondition("m.name", memberName);

      String teamId = req.getParam("teamId");
      JExpressCondition condition2 = new JExpressCondition("t.id", teamId);

      StringBuilder jpql = new StringBuilder("SELECT new simple.userEntity.MemberDto2(m.name, m.engName, m.team) FROM Member m join m.team t");

      JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
      List<MemberDto2> result = jcr.executeJpql(jpql, MemberDto2.class, condition1, condition2);

      res.send(result);

    }, MemberDto2.class);

    // curl -i -X GET "localhost:8020/member/team/gunha"
    app.get("/member/team/:memberName", (req, res)->{

      String memberName = req.getParam("memberName");
      JExpressCondition condition = new JExpressCondition("m.name", memberName);

      StringBuilder jpql = new StringBuilder("SELECT new simple.userEntity.MemberDto3(m.age, m.engName) FROM Member m");

      JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
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

      JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
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

      JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
      Team registerTeam = jcr.registerEntityOrNull(map, Team.class);

      res.send(registerTeam);
    }, Team.class);


        /* body
        {
          "name": "테스트이름",
          "age": "70",
          "engName": "testName",
          "teamId": "1"
        }
        */
    // member team 동시 등록
    app.post("/member/team", (req, res)-> {
      Map<String, String> map = req.getBodyMap();

      Member registerMember = CustomRepository.registerMemberWithTeamOrNull(map);

      res.send(registerMember);
    }, MemberTeamDto.class);

    app.run(8020);
  }
}
```
## 코딩 표준

- [JExpress 코딩 표준](docs/CodingStandard.md)

## 기능
- Cors on/off
- API Docs 자동 생성
  
  ![image](https://github.com/user-attachments/assets/7eba6dca-3ef4-4b33-ac1d-6b8ebad6febf)

- 서버 ResponseTime 측정 on/off
- ThreadPool을 이용한 서버 생성
- DB사용(xml이용)

## 개발 우선 순위
1. 기능 추가(DB연동 기능까지 빠르게 기능 추가)
   - 테스트는 curl과 postman을 통해서 진행
2. 테스트 추가
3. 리팩토링

