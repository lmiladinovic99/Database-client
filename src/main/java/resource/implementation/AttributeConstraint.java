package resource.implementation;

import resource.tree.DBNode;
import resource.enums.ConstraintType;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class AttributeConstraint extends DBNode{
    private ConstraintType constraintType;

    public AttributeConstraint(String name, DBNode parent, ConstraintType constraintType) {
        super(name, parent);
        this.constraintType = constraintType;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return this.getParent();
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
        return true;
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
