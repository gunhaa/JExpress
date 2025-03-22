# SimpREST

- 간단하게 REST API를 만들고 명세가 나오는 서버

## 사용법

```java
        Server app = new SingleThreadServer();
        // 간단하게 get,post 요청과 성공, 실패시 동작을 만들 수 있다
        app.get(
                "/members",
                new ResponseSuccess(200, new Member("gunha", 10)),
                new ResponseError(404, "Not Found")
        );
```

## Todo
- ~~readme~~
- Dockerfile 
- ~~dependency~~
- orm
- ~~singleThread~~
- multiThread
- ~~httpRequest~~
- httpResponse