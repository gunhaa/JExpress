package simple;

import simple.server.Server;
import simple.server.SingleThreadServer;
import simple.tempEntity.Member;
import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        System.out.println("실행 테스트");
        Server app = new SingleThreadServer();

        app.get(
                "/members",
                                                // 원래 해당 위치 DB와 연결되는 람다식을 넘기고 catch가 되면 error로 연결되어야함
                                                // JPA Repo를 이용한 find
                new ResponseSuccess(200, new Member("gunha", 10)),
                new ResponseError(404, "Not Found")
        );

        app.run(8020);
    }
}