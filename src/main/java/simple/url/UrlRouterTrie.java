package simple.url;

public class UrlRouterTrie implements ITrie{

    private final UrlRouterNode root = new UrlRouterNode("/", false);

    // /member/:memberId/team/:teamId stdin
    @Override
    public void insert(String path) {
        String[] parts = path.split("/");
        UrlRouterNode current = this.root;
        System.out.println("insert path : " + path);
        for(String part : parts){
            if(part.isEmpty()){
                continue;
            }
            boolean isDynamic = part.startsWith(":");
            current = current.putAndGetChildNode(part, isDynamic);
        }
        current.setEndPoint();
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
