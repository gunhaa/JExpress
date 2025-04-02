package simple;

import simple.repository.JExpressCRUDRepository;
import simple.repository.JExpressQueryString;
import simple.server.Server;
import simple.server.JExpress;
import simple.tempEntity.Member;

import java.io.IOException;
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

        app.get("/member" , (req, res) -> {
            // url = "/member?name=gunha"
            String key1 = "name";
            String value1 = req.getQueryString(key1);
            JExpressQueryString jqs1 = new JExpressQueryString(key1, value1);

            String key2 = "age";
            String value2 = req.getQueryString(key2);
            JExpressQueryString jqs2 = new JExpressQueryString(key2, value2);

            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            Member findMember = jcr.findSingleTableEntity(Member.class, jqs1, jqs2);

            res.send(findMember);
        });


        app.get("/members", (req, res) -> {
            JExpressCRUDRepository jcr = JExpressCRUDRepository.getInstance();
            List<?> List = jcr.findAll(Member.class);
            res.send(List);
        });

        app.run(8020);
    }
}