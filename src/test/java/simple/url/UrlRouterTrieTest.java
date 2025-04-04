package simple.url;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class UrlRouterTrieTest {

    @Test
    public void Trie구조테스트(){
        UrlRouterTrie urlRouterTrie = new UrlRouterTrie();
        urlRouterTrie.insert("/member/:memberId/team/:teamId");
        urlRouterTrie.insert("/member/:memberId");
        urlRouterTrie.insert("/member");

//        urlRouterTrie.printTrie();

        UrlRouterNode root = urlRouterTrie.getRoot();
        HashMap<String, UrlRouterNode> current = root.getChild();

        for (String depth1 : current.keySet()) {
            UrlRouterNode NodeDepth1 = current.get(depth1);
            Assertions.assertEquals(depth1, "member");
            current = NodeDepth1.getChild();

            for (String depth2 : current.keySet()) {
                UrlRouterNode NodeDepth2 = current.get(depth2);
                Assertions.assertEquals(depth2, ":memberId");
                Assertions.assertTrue(NodeDepth2.isDynamic());
                current = NodeDepth2.getChild();

                for (String depth3 : current.keySet()) {
                    UrlRouterNode NodeDepth3 = current.get(depth3);
                    Assertions.assertEquals(depth3, "team");
                    Assertions.assertFalse(NodeDepth3.isDynamic());
                }
            }
        }

    }
}