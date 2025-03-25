package simple;

import simple.server.Server;
import simple.server.SingleThreadServer;
import simple.tempEntity.Member;
import simple.response.ResponseSuccess;

import java.io.IOException;

import static simple.constant.HttpStatus.OK_200;

public class Main {
    public static void main(String[] args) throws IOException{
        Server app = new SingleThreadServer();

        app.get(
                "/members",
                new ResponseSuccess(OK_200, new Member("gunha", 10))
        );

        app.run(8020);
    }
}