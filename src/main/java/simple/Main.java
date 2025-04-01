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

//        app.use(API_DOCS);
//        app.use(CORS);
        app.use(CORS, "https://bitlibrary.com");
        app.use(RESPONSE_TIME);
        app.use(DB_H2);
//        app.use(DB_MYSQL);
//        app.use(GET_CACHE);

//        app.get("/member" , (req, res) -> {
            // url = "/member?id=3"
            //String qs = req.getQueryString().get("id");
            //res.send(req.findMemberById(qs))
            // 필요한 것
            // 1. reflection으로 getQueryString에서 결과값의 필드를 얻어와서 필드가 맞는지 검사해야함(안해도 npe나게 하면될듯? 사용자 잘못)
            // 2. 그 정보를 이용해서 JPA를 이용해, 객체를 얻을 수 있는 클래스 필요함
            // 3. 결과를 send에 전송
//            String value = req.getQueryString("name");
//            res.send(new Member("gunha", 10), Member.class);
//        });

//        app.get("/user", (req, res) -> {
//            res.send(new Member("jihwan", 47));
//        });
//
//        Member member1 = new Member("gunha", 1);
//        Member member2 = new Member("jaewon", 2);
//        Member member3 = new Member("insoo", 3);
//
//        List<Member> list = new ArrayList<>();
//        list.add(member1);
//        list.add(member2);
//        list.add(member3);
//

        app.get("/members", (req, res) -> {
            List<?> List = req.findAll(Member.class);
            res.send(List);
        });

        app.run(8020);
    }
}