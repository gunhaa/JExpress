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

- POSTMAN 사용시 문제 생길 때 있음(이슈 파악 완료)
  - curl -X GET localhost:8020/members 로는 문제 없음
  - 큰 문제는 아닌 것 같지만, 더 알아보고 해결 해 봐야할듯
    - stackoverflow에 똑같은 사례가 한개 있었는데, 답변이 없었다.. 더 찾아봐야함
  - Connection: close(실패)
  - POSTMAN 설정 변경(실패)
  - BufferedReader.ready() 이슈
    - body를 파악하기 위해 readLine() 이 아닌 read()로 \r\n을 읽을 수 있도록 리팩토링 하였으나 문제 발생
    - POSTMAN을 사용한 두번째 요청에서 해당 ready()상태가 true가 아닌 false로 되어 스트림을 읽지 못함
    - Content-type을 감지하면 그 만큼 body를 읽는 로직으로 수정 예정
      - BufferedReader.ready()의 동작 방식
      - ready()는 현재 버퍼에 읽을 수 있는 데이터가 있는지 확인하는 메서드이다.
      - 문제는 네트워크 소켓의 데이터 도착을 보장하지 않는다.
      - 즉, 요청이 아직 네트워크에서 도착하지 않았으면 false를 반환하여, 요청을 읽지 못하는 상황이 발생할 수 있다.