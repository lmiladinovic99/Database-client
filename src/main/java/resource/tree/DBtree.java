package resource.tree;

import app.AppCore;
import resource.implementation.Entity;
import view.MainFrame;
import view.RightTopPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class DBtree extends JTree implements TreeSelectionListener {

    public DBtree() {
        addTreeSelectionListener(this);
        setCellRenderer(new DBtreeCellRenderer());
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        if (path == null)
            return;
        for (int i = 0; i < path.getPathCount(); i++) {
            DBNode node = (DBNode) path.getPathComponent(i);
            if (node instanceof Entity) {
                Entity entity = (Entity) node;
                MainFrame.getInstance().getAppCore().addRightPanel(entity);
            }
        }
    }
}
