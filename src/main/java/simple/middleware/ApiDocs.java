package simple.middleware;

import simple.apiDocs.ApiDetails;
import simple.apiDocs.ApiDocsDto;
import simple.mapper.GetMapper;
import simple.mapper.IMapper;

import java.util.List;

public class ApiDocs implements IMiddleWare {

    private static volatile ApiDocs INSTANCE;

    private ApiDocs() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static ApiDocs getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiDocs.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiDocs();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        IMapper getMap = GetMapper.getInstance();

        ApiDocsDto apiDocsDto = new ApiDocsDto();
        apiDocsDto.createApiDocs(getMap);
        List<ApiDetails> apiList = apiDocsDto.getApiList();

        getMap.addUrl("/api-docs/v1", (req, res) -> {
            res.send(apiList);
        });

        System.out.println("ApiDocs 미들웨어 실행.. 메소드 작성");
    }

}
