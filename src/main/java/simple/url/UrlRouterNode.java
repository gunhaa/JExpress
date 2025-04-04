package simple.url;

import simple.httpResponse.ILambdaHandler;

import java.util.HashMap;

public class UrlRouterNode {
    private final String path;
    private final HashMap<String, UrlRouterNode> child= new HashMap<>();
    private final boolean isDynamic;
    private boolean isEndPoint;
    private ILambdaHandler iLambdaHandler;

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

    public void setILambdaHandler(ILambdaHandler handler){
        this.iLambdaHandler = handler;
    }

    public ILambdaHandler getILambdaHandler(){
        return this.iLambdaHandler;
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
}
