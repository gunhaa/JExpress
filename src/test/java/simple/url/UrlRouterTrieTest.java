package simple.url;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import simple.httpRequest.LambdaHttpRequest;
import simple.httpResponse.ILambdaHandler;
import simple.httpResponse.LambdaHttpResponse;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlRouterTrieTest {

    @Test
    public void TDD_구조테스트(){
        UrlRouterTrie urlRouterTrie = new UrlRouterTrie();
        ILambdaHandler mock = new ILambdaHandler() {
            @Override
            public void execute(LambdaHttpRequest lambdaHttpRequest, LambdaHttpResponse lambdaHttpResponse) {

            }
        };
        urlRouterTrie.insert("/member/:memberId/team/:teamId", mock);
        urlRouterTrie.insert("/member/:memberId", mock);
        urlRouterTrie.insert("/member", mock);

//        urlRouterTrie.printTrie();

        UrlRouterNode root = urlRouterTrie.getRoot();
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
    public void TDD_URL에_맞는_Handler반환(){
        UrlRouterTrie urlRouterTrie = new UrlRouterTrie();

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

        ILambdaHandler mock1 = (lambdaHttpRequest, lambdaHttpResponse) -> testHolder1.setPath("/member");
        ILambdaHandler mock2 = (lambdaHttpRequest, lambdaHttpResponse) -> testHolder2.setPath("/member/:memberId");

        urlRouterTrie.insert("/member", mock1);
        urlRouterTrie.insert("/member/:memberId", mock2);

        ILambdaHandler lambda1 = urlRouterTrie.getLambdaHandlerOrNull("/member");
        ILambdaHandler lambda2 = urlRouterTrie.getLambdaHandlerOrNull("/member/3");

        LambdaHttpRequest dummyRequest = Mockito.mock(LambdaHttpRequest.class);
        LambdaHttpResponse dummyResponse = Mockito.mock(LambdaHttpResponse.class);

        lambda1.execute(dummyRequest, dummyResponse);
        lambda2.execute(dummyRequest, dummyResponse);

        Assertions.assertEquals( "/member", testHolder1.getPath());
        Assertions.assertEquals("/member/:memberId", testHolder2.getPath());

        // 쿼리 스트링이 url에 있을 경우
        ILambdaHandler lambda3 = urlRouterTrie.getLambdaHandlerOrNull("/member?name=asd");
        lambda3.execute(dummyRequest, dummyResponse);

        Assertions.assertEquals( "/member", testHolder1.getPath());
        
    }
}