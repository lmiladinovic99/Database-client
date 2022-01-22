package resource.implementation;

import resource.tree.DBNode;
import resource.tree.DBNodeComposite;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class InformationResource extends DBNodeComposite {

    public InformationResource(String name) {
        super(name, null);
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Entity){
            Entity entity = (Entity) child;
            this.getChildren().add(entity);
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return this.getChildren().get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.getChildren().size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
