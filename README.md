# JExpress

- json을 반환하는 REST API를 만들수 있는 웹 프레임워크

## 사용예제

```java
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
```
## 코딩 표준

- [JExpress 코딩 표준](docs/CodingStandard.md)

## 주요 기능

### CORS
- `app.use(CORS)`
  - 기본 설정: `Access-Control-Allow-Origin: *`
- `app.use(CORS, "https://bitlibrary.kro.kr")`
  - 커스텀 설정: `Access-Control-Allow-Origin: https://bitlibrary.kro.kr`

### Response Time
- `app.use(RESPONSE_TIME)`
  - 각 요청의 응답 시간을 출력

### ThreadPool
- 기본 스레드 풀(1개)  
  `IServer app = new JExpress()`
- 사용자 정의 스레드 풀  
  `IServer app = new JExpress(30)` (예: 30개의 스레드)

### DB 설정 (`persistence.xml` 설정 사용)
- H2 사용  
  `app.use(DB_H2)`
- MySQL 사용
  `app.use(DB_MYSQL)`

### API 문서 자동 생성
- `app.use(API_DOCS)`
- 생성 위치 : `http://localhost:port/api-docs.html`

  ![api-docs](docs/images/api-docs1.png)

## 실행 환경

- Java 17
- Gradle
- DB(persistence.xml로 설정 관리)
  - MySQL (설정 시 사용)
  - 설정하지 않을 경우 H2 In-Memory 모드로 자동 동작

## 개발 우선 순위
1. 기능 추가
   - 테스트는 curl과 postman을 통해서 진행하며, 기능이 중요한 경우 TDD로 개발 진행
2. 테스트 추가
3. 리팩토링

## 사용 방법