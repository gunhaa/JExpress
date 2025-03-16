package simple;

import simple.server.SingleThreadServer;
import simple.tempEntity.Member;
import simple.tempEntity.ResponseError;
import simple.tempEntity.ResponseSuccess;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        System.out.println("실행 테스트");
        simple.server.Server app = new SingleThreadServer();

        app.get(
                "/members",
                new ResponseSuccess(200, new Member("gunha", 10)),
                new ResponseError(400, "message")
        );

        app.run(8020);
    }
}