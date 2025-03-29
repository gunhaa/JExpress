package simple.response;

import simple.constant.ApplicationSetting;
import simple.context.ApplicationConfig;
import simple.httpRequest.SimpleHttpRequest;

import java.io.PrintWriter;

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

        int config = ApplicationConfig.getConfig();

        if(ApplicationSetting.API_DOCS.isEnabled(config)) {
            System.out.println("Config 상태에 따른 메서드 선택 예정");
            System.out.println("config : "+config);
        }

        StringBuilder response = responseBuilder.getDefaultResponse();
        pw.print(response);
    };
}
