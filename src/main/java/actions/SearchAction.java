package actions;

import resource.implementation.Entity;
import view.AddDialog;
import view.MainFrame;
import view.RightTopPanel;
import view.SearchDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchAction extends AbstractAction implements ActionListener {

    public SearchAction() {
        putValue(NAME, "Search");
        putValue(SHORT_DESCRIPTION, "Search table");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Entity entity= (Entity) ((RightTopPanel) MainFrame.getInstance().getTopTab().getSelectedComponent()).getEntity();
        SearchDialog searchDialog=new SearchDialog(entity);
        searchDialog.setVisible(true);
    }
}
