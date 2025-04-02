package simple.apiDocs;

import simple.httpRequest.HttpRequest;
import simple.httpRequest.LambdaHttpRequest;
import simple.response.LambdaHandler;
import simple.response.LambdaHttpResponse;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiDocsDto {

    private List<ApiDetails> apiList = new ArrayList<>();

    public void createProxy(Map<String, LambdaHandler> apiHandlers) {

        for (Map.Entry<String, LambdaHandler> entry : apiHandlers.entrySet()) {
            String url = entry.getKey();
            LambdaHandler handler = entry.getValue();

            HttpRequest mock = HttpRequest.createMock();
            LambdaHttpRequest lambdaHttpRequest = new LambdaHttpRequest(mock);

            LambdaHttpResponse response = new LambdaHttpResponse(lambdaHttpRequest, new PrintWriter(System.out, true));

            handler.execute(null, response);

            Class<?> returnType = LastSentObjectHolder.getLastSentType();
            if (returnType != null) {
                ApiDetails apiDetails = new ApiDetails(url, returnType.getSimpleName());

                if(returnType.getPackage().getName().startsWith("java.util")){
                    apiDetails.addField("collection", returnType.getSimpleName());
                } else {
                    Field[] fields = returnType.getDeclaredFields();
                    for (Field field : fields) {
                        apiDetails.addField(field.getName(), field.getType().getSimpleName());
                    }
                }
                apiList.add(apiDetails);
            }


        }
    }

    public List<ApiDetails> getApiList(){
        return this.apiList;
    }

};

