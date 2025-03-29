package simple.response;

import simple.config.ApplicationConfig;
import simple.httpRequest.SimpleHttpRequest;

import java.io.PrintWriter;

import static simple.constant.ApplicationSetting.*;

public class LambdaHttpResponse {

    private final SimpleHttpRequest simpleHttpRequest;
    private final PrintWriter pw;

    public LambdaHttpResponse(SimpleHttpRequest simpleHttpRequest, PrintWriter pw) {
        this.simpleHttpRequest = simpleHttpRequest;
        this.pw = pw;
    }

    public void send(Object responseBody){
        ResponseBuilder responseBuilder = new ResponseBuilder(simpleHttpRequest, responseBody);
        // config의 상태에 따라 사용 메서드 변경(cors, api-docs)

        ResponseBuilder responseBuilding = responseBuilder.getDefaultResponse();

        int config = ApplicationConfig.getInstance().getConfig();
        if(API_DOCS.isEnabled(config)) {
            //API_DOCS 로직, 추가 위치
//            responseBuilding.cors();
        }

        if(CORS.isEnabled(config)){
            System.out.println("이곳 잘 실행됨.. enabled 통과됨");
            responseBuilding.cors();
        }

        StringBuilder response = responseBuilding.getResponse();
        pw.print(response);
    };
}
