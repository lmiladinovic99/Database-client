package resource.tree;

import javax.swing.tree.TreeNode;

public abstract class DBNode implements TreeNode {
    private String name;
    private DBNode parent;

    public String getName() {
        return name;
    }

    public DBNode(String name, DBNode parent) {
        this.name=name;
        this.parent=parent;
    }

    public DBNode getDBNodeParent() {
        return parent;
    }

    @Override
    public String toString() {
        return name;
    }
}