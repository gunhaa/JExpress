# JExpress

- 빠르게 json을 반환하는 API를 만들수 있는 웹 프레임워크

## 사용예제

```java
public class Main {
  public static void main(String[] args) throws IOException{
    Server app = new JExpress();
    // threadPool을 이용한 서버 생성방법
    // Server app = new JExpress(15);

    app.use(API_DOCS);
//        app.use(CORS);
    app.use(CORS, "https://bitlibrary.com");
    app.use(RESPONSE_TIME);
//        app.use(GET_CACHE);

    app.get("/member" , (req, res) -> {
      // url = "/member?id=3"
      //String qs = req.getQueryString().get("id");
      //res.send(req.findMemberById(qs))
      res.send(new Member("gunha", 10));
    });

    app.get("/user", (req, res) -> {
      res.send(new Member("jihwan", 47));
    });

    Member member1 = new Member("gunha", 1);
    Member member2 = new Member("jaewon", 2);
    Member member3 = new Member("insoo", 3);

    List<Member> list = new ArrayList<>();
    list.add(member1);
    list.add(member2);
    list.add(member3);

    app.get("/members", (req, res) -> {
      res.send(list);
    });

    app.run(8020);
  }
}
```
## 코딩 표준

- [POCU아카데미의 코딩 표준을 따른다.](https://docs.popekim.com/ko/coding-standards/pocu-java)

## 기능
- Cors on/off
- API Docs 자동 생성
  
  ![image](https://github.com/user-attachments/assets/7eba6dca-3ef4-4b33-ac1d-6b8ebad6febf)

- 서버 ResponseTime 측정 on/off

## 개발 우선 순위
1. 기능 추가(DB연동 기능까지 빠르게 기능 추가)
   - 테스트는 curl과 postman을 통해서 진행
2. 테스트 추가
3. 리팩토링

## ISSUE

### BufferedReader.ready() 이슈

#### 문제 상황
- `readLine()` 대신 `read()`를 사용하여 `\r\n`을 직접 읽도록 리팩토링했으나 문제 발생
- POSTMAN에서 두 번째 요청 시 `ready()` 상태가 `false`가 되어 스트림을 읽지 못함
- `curl -X GET` 요청 시 문제 없음

#### 원인 분석
- `BufferedReader.ready()`는 현재 버퍼에 읽을 데이터가 있는지 확인하는 메서드
  - 네트워크 소켓에서 데이터 도착을 보장하지 않음
  - 요청이 아직 네트워크에서 도착하지 않았다면 `false`를 반환 → 스트림을 읽지 못하는 문제 발생

#### 해결 방향
- `ready()`를 사용하지 않고 추가 로직을 구현하여 해결 가능
- `Content-Length` 헤더를 포함하지 않고 `Body`를 넣는 사용자를 생각해 `read()`로 리팩토링을 하였으나..
  - `read()`를 계속 사용하며 로직 수정을 하는 방법도 있으나, `Content-Length` 헤더를 감지하여 그만큼 Body를 읽는 방식으로 변경 예정
  - 형식을 지키는 것이 서버에게 더 중요한 것 같아 잘못된 요청의 경우 `400_BAD_REQUEST` 를 내보내고 `read()`에서  `readLine()` 방식으로 변경 하는 것이 더 좋을 것이라고 판단 해 변경 예정
