package simple.url;

import org.junit.jupiter.api.Test;

class UrlRouterTrieTest {

    @Test
    public void 삽입테스트(){
        UrlRouterTrie urlRouterTrie = new UrlRouterTrie();
        urlRouterTrie.insert("/member/:memberId/team/:teamId");
        urlRouterTrie.insert("/member/:memberId/parents");
        urlRouterTrie.insert("/member/:memberId");
        urlRouterTrie.insert("/member");

        urlRouterTrie.printTrie();
    }
}