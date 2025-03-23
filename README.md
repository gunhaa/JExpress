# SimpREST

- 간단하게 REST API를 만들고 명세가 나오는 서버

## 사용법

```java
        Server app = new SingleThreadServer();
        // 간단하게 get 요청을 만들 수 있다
        app.get("/members", new ResponseSuccess(OK_200, new Member("gunha", 10)), new ResponseError(BAD_REQUEST_400));

                app.run(8020);
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

- POSTMAN 사용시 문제 생길 때 있음
  - curl -X GET localhost:8020/members 로는 문제 없음
  - 큰 문제는 아닌 것 같지만, 더 알아보고 해결 해 봐야할듯
    - stackoverflow에 똑같은 사례가 한개 있었는데, 답변이 없었다.. 더 찾아봐야함
  - Connection: close(실패)
  - POSTMAN 설정 변경(실패)