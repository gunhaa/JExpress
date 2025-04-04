package simple.url;

import java.util.HashMap;

public class UrlRouterNode {
    private final String path;
    private final HashMap<String, UrlRouterNode> child= new HashMap<>();
    private final boolean isDynamic;
    private boolean isEndPoint;

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

    public String getPath(){
        return this.path;
    }

    public HashMap<String, UrlRouterNode> getChild(){
        return this.child;
    }

    public boolean isDynamic(){
        return this.isDynamic;
    }

    public boolean isEndPoint(){
        return this.isEndPoint;
    }
}
