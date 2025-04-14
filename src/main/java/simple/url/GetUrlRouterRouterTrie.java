package simple.url;

import simple.decoder.URLDecoder;
import simple.httpRequest.HttpRequest;
import simple.lambda.LambdaHandlerWrapper;

import java.util.Arrays;

public class GetUrlRouterRouterTrie implements IRouterTrie {

    private static final UrlRouterNode root = new UrlRouterNode("/", false);
    private static final GetUrlRouterRouterTrie INSTANCE = new GetUrlRouterRouterTrie();

    private GetUrlRouterRouterTrie() {
    }

    public static GetUrlRouterRouterTrie getInstance(){
        return INSTANCE;
    }

    // /member/:memberId/team/:teamId stdin
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
                String rawValue = httpRequest.getUrl().split("/")[depth+1];
                String value = URLDecoder.decodeURL(rawValue);
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

    // for test
    public void printTrie() {
        printTrieRecursive(root, 0);
    }

    // for test
    private void printTrieRecursive(UrlRouterNode node, int depth) {
        String indent = "===".repeat(depth);
        System.out.println(indent + "|-- " + node.getPath() +
                (node.isDynamic() ? " (Dynamic)" : "") +
                (node.isEndPoint() ? " [END]" : ""));

        for (UrlRouterNode child : node.getChild().values()) {
            printTrieRecursive(child, depth + 1);
        }
    }

    // for test
    public void clearTrie(){
        root.clearChild();
    }
}
