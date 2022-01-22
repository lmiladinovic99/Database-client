package resource.implementation;

import resource.enums.ConstraintType;
import resource.tree.DBNode;
import resource.tree.DBNodeComposite;
import resource.enums.AttributeType;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Objects;

public class Attribute extends DBNodeComposite {
    public AttributeType attributeType;
    private int length;
    private Attribute inRealationWith;
    private boolean isPrimaryKey;
    private boolean isForeignKey;

    public Attribute(String name, DBNode parent, AttributeType attributeType, int length) {
        super(name, parent);
        this.attributeType=attributeType;
        this.length=length;
    }

    @Override
    public void addChild(DBNode child) {
        if(child!=null && child instanceof AttributeConstraint) {
            AttributeConstraint attributeConstraint=(AttributeConstraint) child;
            this.getChildren().add(attributeConstraint);
        }
    }

    public boolean isPrimaryKey() {
        for (int i = 0; i < this.getChildCount(); i++) {
            AttributeConstraint attributeConstraint = (AttributeConstraint) this.getChildAt(i);
            if (attributeConstraint.getConstraintType() == ConstraintType.PRIMARY_KEY) {
                isPrimaryKey = true;
                return isPrimaryKey;
            }
        }
        isPrimaryKey = false;
        return isPrimaryKey;
    }

    public boolean isForeignKey() {
        for (int i = 0; i < this.getChildCount(); i++) {
            AttributeConstraint attributeConstraint = (AttributeConstraint) this.getChildAt(i);
            if (attributeConstraint.getConstraintType() == ConstraintType.FOREIGN_KEY) {
                isForeignKey = true;
                return isForeignKey;
            }
        }
        isForeignKey = false;
        return isForeignKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return this.getName().equals(attribute.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeType, length, inRealationWith, isPrimaryKey);
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public int getLength() {
        return length;
    }

    public Attribute getInRealationWith() {
        return inRealationWith;
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
}
