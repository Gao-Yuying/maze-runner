package maze.models;

import java.util.List;

public  class Node {
    public int x;
    public int y;
    public Node parent;
    public List<Node> children;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        parent = null;
        children = null;
    }

    public Node(int x, int y, Node parent, List<Node> children) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.children = children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (!(obj instanceof Node)) { return false; }
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }
}
