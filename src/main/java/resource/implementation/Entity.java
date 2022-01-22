package resource.implementation;

import resource.tree.DBNode;
import resource.tree.DBNodeComposite;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class Entity extends DBNodeComposite {
    private List<String> inRelationWith;

    public Entity(String name, DBNode parent) {
        super(name, parent);
        inRelationWith=new ArrayList<>();
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
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

    public void addRelationTable(String tableName) {
        if(!inRelationWith.contains(tableName) && !tableName.equals(this.getName()))
            inRelationWith.add(tableName);
    }

    public List<String> getInRelationWith() {
        return inRelationWith;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return this.getName().equals(entity.getName());
    }
}
