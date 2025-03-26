# SimpREST

- 간단하게 REST API를 만들고 명세가 나오는 서버

## 사용법

```java
        Server app = new SingleThreadServer();
        // 간단하게 get 요청을 만들 수 있다
        app.get("/members", new ResponseSuccess(OK_200, new Member("gunha", 10)), new ResponseError(BAD_REQUEST_400));

                app.run(8020);
```

- 람다식으로 변경 예정

```java
        Server app = new SingleThreadServer();
        // 간단하게 get 요청을 만들 수 있다
        app.get("/members" , (req, res) -> {
            String qs = req.getQueryString().get("id");
            res.send(new Member("gunha", 10)), OK_200);
//            res.send(findById(Member.class, qs), OK_200);
        });
```

## Todo

- ~~readme~~
- Dockerfile 
- ~~dependency~~
- orm
  - orm을 이용한 response 반환 추가예정
- ~~singleThread~~
- multiThread
- ~~httpRequest~~
- ~~httpResponse~~

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
