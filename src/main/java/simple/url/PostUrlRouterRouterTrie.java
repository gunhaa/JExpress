package simple.url;

import simple.httpRequest.HttpRequest;
import simple.lambda.LambdaHandlerWrapper;

import java.util.Arrays;

public class PostUrlRouterRouterTrie implements IRouterTrie {

    private static final UrlRouterNode root = new UrlRouterNode("/", false);
    private static final PostUrlRouterRouterTrie INSTANCE = new PostUrlRouterRouterTrie();

    private PostUrlRouterRouterTrie() {
    }

    public static PostUrlRouterRouterTrie getInstance(){
        return INSTANCE;
    }

    @Override
    public void insert(String url, LambdaHandlerWrapper handler) {
        String[] parts = url.split("/");
        UrlRouterNode current = root;
        for(String part : parts){
            if(part.isEmpty()){
                continue;
            }
            boolean isDynamic = part.startsWith(":");
            current = current.putAndGetChildNode(part, isDynamic);
        }
        current.setILambdaHandler(handler);
        current.setEndPoint();
    }

    @Override
    public LambdaHandlerWrapper getLambdaHandlerOrNull(HttpRequest httpRequest){

        if(httpRequest.getUrl()==null){
            return null;
        }

        String realPath = httpRequest.getUrl().split("\\?")[0];

        String[] parts = Arrays.stream(realPath.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        return searchLambdaHandlerRecursive(root, parts, 0, httpRequest);
    }

    private LambdaHandlerWrapper searchLambdaHandlerRecursive(UrlRouterNode node, String[] parts, int depth, HttpRequest httpRequest){
        if(depth == parts.length){
            return node.getILambdaHandler();
        }

        String part = parts[depth];

        if(node.getChild().containsKey(part)){
            return searchLambdaHandlerRecursive(node.getChildNode(part), parts, depth+1, httpRequest);
        }

        for(UrlRouterNode child : node.getChild().values()){
            if(child.isDynamic()){
                String key = child.getPath().replace(":", "");
                String value = httpRequest.getUrl().split("/")[depth+1];
                httpRequest.setParams(key , value);
                return searchLambdaHandlerRecursive(child, parts, depth+1, httpRequest);
            }
        }

        return null;
    }

    @Override
    public UrlRouterNode getRoot(){
        return root;
    }

    public void printTrie() {
        printTrieRecursive(root, 0);
    }

    private void printTrieRecursive(UrlRouterNode node, int depth) {
        String indent = "===".repeat(depth);
        System.out.println(indent + "|-- " + node.getPath() +
                (node.isDynamic() ? " (Dynamic)" : "") +
                (node.isEndPoint() ? " [END]" : ""));

        for (UrlRouterNode child : node.getChild().values()) {
            printTrieRecursive(child, depth + 1);
        }
    }

}
