package simple;

import simple.constant.ApplicationSetting;
import simple.server.Server;
import simple.server.SingleThreadServer;
import simple.tempEntity.Member;
import simple.response.ResponseSuccess;

import java.io.IOException;

import static simple.constant.ApplicationSetting.API_DOCS;
import static simple.constant.HttpStatus.OK_200;

public class Main {
    public static void main(String[] args) throws IOException{
        Server app = new SingleThreadServer();

        app.use(API_DOCS , true);

        app.get("/members" , (req, res) -> {
//            String qs = req.getQueryString().get("id");
                    res.send(new Member("gunha", 10));
        });

        app.run(8020);
    }
}