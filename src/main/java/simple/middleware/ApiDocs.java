package simple.middleware;

import simple.apiDocs.ApiDetails;
import simple.apiDocs.ApiDocsDto;
import simple.mapper.GetMapper;
import simple.mapper.IMapper;
import simple.mapper.PostMapper;

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
        IMapper postMap = PostMapper.getInstance();

        ApiDocsDto apiDocsDto = new ApiDocsDto();
        List<ApiDetails> getDocs = apiDocsDto.createApiDocs(getMap);
        List<ApiDetails> postDocs = apiDocsDto.createApiDocs(postMap);

        getMap.addUrl("/api-docs/get/v1", (req, res) -> {
            res.send(getDocs);
        });

        getMap.addUrl("/api-docs/post/v1", (req, res)-> {
            res.send(postDocs);
        });

        System.out.println("ApiDocs 미들웨어 실행.. 메소드 작성");
    }

}
