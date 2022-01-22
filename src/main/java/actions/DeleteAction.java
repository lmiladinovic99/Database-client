package actions;

import resource.implementation.Entity;
import view.DeleteDialog;
import view.MainFrame;
import view.RightTopPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAction extends AbstractAction implements ActionListener {

    public DeleteAction() {
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete row");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity entity= (Entity) ((RightTopPanel) MainFrame.getInstance().getTopTab().getSelectedComponent()).getEntity();
        DeleteDialog deleteDialog=new DeleteDialog(entity);
        deleteDialog.setVisible(true);
    }
}
