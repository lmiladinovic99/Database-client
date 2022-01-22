package resource.tree;

import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class DBtreeCellRenderer extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if(value instanceof DBNode) {

            if(value instanceof InformationResource) {
                URL imageURL=getClass().getResource("images/database.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);

            } else if(value instanceof Entity) {
                URL imageURL=getClass().getResource("images/entity.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            } else if(value instanceof Attribute) {
                URL imageURL=getClass().getResource("images/attribute.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            } else if(value instanceof AttributeConstraint) {
                URL imageURL=getClass().getResource("images/constraint.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }
        }


        return this;
    }
}
