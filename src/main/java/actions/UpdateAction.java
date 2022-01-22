package actions;

import resource.implementation.Entity;
import view.DeleteDialog;
import view.MainFrame;
import view.RightTopPanel;
import view.UpdateDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateAction extends AbstractAction implements ActionListener {

    public UpdateAction() {
        putValue(NAME, "Update");
        putValue(SHORT_DESCRIPTION, "Update Row");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity entity= (Entity) ((RightTopPanel) MainFrame.getInstance().getTopTab().getSelectedComponent()).getEntity();
        UpdateDialog updateDialog=new UpdateDialog(entity);
        updateDialog.setVisible(true);
    }
}
