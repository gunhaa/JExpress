# SimpREST

- 간단하게 REST API를 만들고 명세가 나오는 서버

## 사용법

```java
        Server app = new SingleThreadServer();
        // 간단하게 get 요청을 만들 수 있다
        app.get(
                "/members",
                        new ResponseSuccess(OK_200, new Member("gunha", 10)),
        new ResponseError(BAD_REQUEST_400, "Not Found")
        );

                app.run(8020);
```

## Todo

- ~~readme~~
- Dockerfile 
- ~~dependency~~
- orm
  - orm을 이용한 response 반환 추가
- ~~singleThread~~
- multiThread
- ~~httpRequest~~
- ~~httpResponse~~