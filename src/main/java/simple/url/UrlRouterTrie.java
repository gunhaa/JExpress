package simple.url;

import simple.httpResponse.LambdaHandlerWrapper;

import java.util.Arrays;

public class UrlRouterTrie implements ITrie{

    private final UrlRouterNode root = new UrlRouterNode("/", false);

    // /member/:memberId/team/:teamId stdin
    @Override
    public void insert(String url, LambdaHandlerWrapper handler) {
        String[] parts = url.split("/");
        UrlRouterNode current = this.root;
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

    public LambdaHandlerWrapper getLambdaHandlerOrNull(String url){

        String realPath = url.split("\\?")[0];

        String[] parts = Arrays.stream(realPath.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        return searchLambdaHandlerRecursive(root, parts, 0);
    }

    private LambdaHandlerWrapper searchLambdaHandlerRecursive(UrlRouterNode node, String[] parts, int depth){
        if(depth == parts.length){
            return node.getILambdaHandler();
        }
        
        String part = parts[depth];
        
        if(node.getChild().containsKey(part)){
            return searchLambdaHandlerRecursive(node.getChildNode(part), parts, depth+1);
        }

        for(UrlRouterNode child : node.getChild().values()){
            if(child.isDynamic()){
                return searchLambdaHandlerRecursive(child, parts, depth+1);
            }
        }

        return null;
    }

    public UrlRouterNode getRoot(){
        return this.root;
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
