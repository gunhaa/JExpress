package simple.url;

import simple.lambda.LambdaHandlerWrapper;

import java.util.HashMap;

public class UrlRouterNode {
    private final String path;
    private final HashMap<String, UrlRouterNode> child = new HashMap<>();
    private final boolean isDynamic;
    private boolean isEndPoint;
    private LambdaHandlerWrapper lambdaHandlerWrapper;

    public UrlRouterNode(String path, boolean isDynamic) {
        this.path = path;
        this.isDynamic = isDynamic;
    }

    public UrlRouterNode putAndGetChildNode(String part, boolean flag){
        this.child.putIfAbsent(part, new UrlRouterNode(part, flag));
        return this.child.get(part);
    }

    public void setEndPoint(){
        this.isEndPoint = true;
    }

    public void setILambdaHandler(LambdaHandlerWrapper handler){
        this.lambdaHandlerWrapper = handler;
    }

    public LambdaHandlerWrapper getILambdaHandler(){
        return this.lambdaHandlerWrapper;
    }

    public String getPath(){
        return this.path;
    }

    public HashMap<String, UrlRouterNode> getChild(){
        return this.child;
    }

    public UrlRouterNode getChildNode(String part){
        return this.child.get(part);
    }

    public boolean isDynamic(){
        return this.isDynamic;
    }

    public boolean isEndPoint(){
        return this.isEndPoint;
    }

    // for test
    public void clearChild(){
        this.child.clear();
    }
}
