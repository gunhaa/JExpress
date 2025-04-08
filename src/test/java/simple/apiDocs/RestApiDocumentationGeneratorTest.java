package simple.apiDocs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import simple.mapper.GetMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

class RestApiDocumentationGeneratorTest {

    static class TestClazz {
        private Map field1;
    }

    @Test
    public void Mapper_Integration_테스트(){
        GetMapper getMap = GetMapper.createGetMapper();

        getMap.addUrl("/test", (req, res)->{}, TestClazz.class);
        getMap.addUrl("/list", (req, res)->{}, List.class);
        getMap.addUrl("/int", (req, res)->{}, int.class);
        RestApiDocumentationGenerator restApiDocumentationGenerator = new RestApiDocumentationGenerator();
        List<ApiDetail> getDocs = restApiDocumentationGenerator.extractApiDetails(getMap);

        // 생성할때 map을 이용하기 때문에 순서가 없어서 오름차순으로 정렬
        getDocs.sort(Comparator.comparing(ApiDetail::getUrl));
        
        ApiDetail num = getDocs.get(0);
        ApiDetail list = getDocs.get(1);
        ApiDetail test = getDocs.get(2);

        Assertions.assertEquals("/int", num.getUrl());
        Assertions.assertEquals("/list", list.getUrl());
        Assertions.assertEquals("/test", test.getUrl());

        Assertions.assertEquals("int", num.getReturnType());
        Assertions.assertEquals("List", list.getReturnType());
        Assertions.assertEquals("TestClazz", test.getReturnType());

        Map<String, String> listFields = list.getFields();
        Assertions.assertEquals("List", listFields.get("표준 자바 클래스"));

        Map<String, String> testFields = test.getFields();
        Assertions.assertEquals("Map", testFields.get("field1"));
    }
}