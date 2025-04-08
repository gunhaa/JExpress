package simple.url;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import simple.httpRequest.HttpRequest;
import simple.lambda.LambdaHttpRequest;
import simple.lambda.ILambdaHandler;
import simple.lambda.LambdaHandlerWrapper;
import simple.lambda.LambdaHttpResponse;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetUrlRouterTrieTest {

    @Test
    @DisplayName("TDD_구조테스트")
    public void testRouterTrieStructure(){
        GetUrlRouterRouterTrie getUrlRouterTrie = GetUrlRouterRouterTrie.getInstance();
        ILambdaHandler iLambdaHandler = new ILambdaHandler() {
            @Override
            public void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse) {

            }
        };

        LambdaHandlerWrapper mock = new LambdaHandlerWrapper(iLambdaHandler);

        getUrlRouterTrie.insert("/member/:memberId/team/:teamId", mock);
        getUrlRouterTrie.insert("/member/:memberId", mock);
        getUrlRouterTrie.insert("/member", mock);

//        urlRouterTrie.printTrie();

        UrlRouterNode root = getUrlRouterTrie.getRoot();
        HashMap<String, UrlRouterNode> current = root.getChild();

        for (String depth1 : current.keySet()) {
            UrlRouterNode nodeDepth1 = current.get(depth1);
            assertEquals("member", depth1);
            current = nodeDepth1.getChild();

            for (String depth2 : current.keySet()) {
                UrlRouterNode nodeDepth2 = current.get(depth2);
                assertEquals(":memberId", depth2);
                Assertions.assertTrue(nodeDepth2.isDynamic());
                current = nodeDepth2.getChild();

                for (String depth3 : current.keySet()) {
                    UrlRouterNode nodeDepth3 = current.get(depth3);
                    assertEquals("team", depth3);
                    Assertions.assertFalse(nodeDepth3.isDynamic());
                    current = nodeDepth3.getChild();

                    for (String depth4 : current.keySet()) {
                        UrlRouterNode nodeDepth4 = current.get(depth4);
                        assertEquals(":teamId", depth4);
                        Assertions.assertTrue(nodeDepth4.isEndPoint());
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("URL에 맞는 핸들러 반환 테스트")
    public void testReturnHandlerMatchingUrl(){
        GetUrlRouterRouterTrie getUrlRouterTrie = GetUrlRouterRouterTrie.getInstance();

        class TestHolder {
            private String path;

            public void setPath(String input){
                this.path = input;
            }

            public String getPath(){
                return this.path;
            }

        }

        TestHolder testHolder1 = new TestHolder();
        TestHolder testHolder2 = new TestHolder();

        ILambdaHandler iLambdaHandler1 = (lambdaHttpRequest, lambdaHttpResponse) -> testHolder1.setPath("/member");
        ILambdaHandler iLambdaHandler2 = (lambdaHttpRequest, lambdaHttpResponse) -> testHolder2.setPath("/member/:memberId");

        LambdaHandlerWrapper mock1 = new LambdaHandlerWrapper(iLambdaHandler1);
        LambdaHandlerWrapper mock2 = new LambdaHandlerWrapper(iLambdaHandler2);

        getUrlRouterTrie.insert("/member", mock1);
        getUrlRouterTrie.insert("/member/:memberId", mock2);

        HttpRequest mockRequest1 = Mockito.mock(HttpRequest.class);
        Mockito.when(mockRequest1.getUrl()).thenReturn("/member");

        HttpRequest mockRequest2 = Mockito.mock(HttpRequest.class);
        Mockito.when(mockRequest2.getUrl()).thenReturn("/member/:memberId");

        LambdaHandlerWrapper lambda1 = getUrlRouterTrie.getLambdaHandlerOrNull(mockRequest1);
        LambdaHandlerWrapper lambda2 = getUrlRouterTrie.getLambdaHandlerOrNull(mockRequest2);

        LambdaHttpRequest dummyRequest = Mockito.mock(LambdaHttpRequest.class);
        LambdaHttpResponse dummyResponse = Mockito.mock(LambdaHttpResponse.class);

        lambda1.unwrap().execute(dummyRequest, dummyResponse);
        lambda2.unwrap().execute(dummyRequest, dummyResponse);

        Assertions.assertEquals( "/member", testHolder1.getPath());
        Assertions.assertEquals("/member/:memberId", testHolder2.getPath());


        HttpRequest mockRequest3 = Mockito.mock(HttpRequest.class);
        Mockito.when(mockRequest3.getUrl()).thenReturn("/member?name=asd");
        // 쿼리 스트링이 url에 있을 경우
        LambdaHandlerWrapper lambda3 = getUrlRouterTrie.getLambdaHandlerOrNull(mockRequest3);
        lambda3.unwrap().execute(dummyRequest, dummyResponse);

        Assertions.assertEquals( "/member", testHolder1.getPath());
    }

    @BeforeEach
    @DisplayName("트라이 초기화")
    public void initializeRouterTrie(){
        GetUrlRouterRouterTrie.getInstance().clearTrie();
    }
}