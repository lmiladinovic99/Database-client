package actions;

import resource.implementation.Entity;
import view.ReportsDialog;
import view.MainFrame;
import view.RightTopPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsAction extends AbstractAction implements ActionListener {

    public ReportsAction() {
        putValue(NAME, "Reports");
        putValue(SHORT_DESCRIPTION, "Reports table");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity entity= (Entity) ((RightTopPanel) MainFrame.getInstance().getTopTab().getSelectedComponent()).getEntity();
        ReportsDialog countAverageDialog = new ReportsDialog(entity);
        countAverageDialog.setVisible(true);
    }
}
