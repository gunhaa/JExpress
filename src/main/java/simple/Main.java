package simple;

import simple.server.Server;
import simple.server.JExpress;
import simple.tempEntity.Member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static simple.constant.ApplicationSetting.*;

public class Main {
    public static void main(String[] args) throws IOException{
        Server app = new JExpress();
        // threadPool을 이용한 서버 생성방법
        // Server app = new JExpress(15);

        app.use(API_DOCS);
//        app.use(CORS);
        app.use(CORS, "https://bitlibrary.com");
        app.use(RESPONSE_TIME);
        app.use(DB_H2);
//        app.use(DB_MYSQL);
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