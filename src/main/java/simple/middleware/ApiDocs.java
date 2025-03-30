package simple.middleware;

import simple.mapper.GetMapper;
import simple.mapper.Mapper;

public class ApiDocs implements Middleware{

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
        Mapper getMap = GetMapper.getInstance();
        getMap.addUrl("/api-docs/v1", (req, res) -> {
            // getMap의 정보를 전부 읽어와서 내보내야함
            // 메소드(GET), 리턴값(클래스명, 필드[리스트]), url 합쳐서 send()에 obj 로 전송
//            res.send();
        });
        System.out.println("ApiDocs 미들웨어 실행.. 메소드 작성");
    }

}
