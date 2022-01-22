package actions;

import resource.implementation.Entity;
import view.AddDialog;
import view.FilterSortDialog;
import view.MainFrame;
import view.RightTopPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterSortAction extends AbstractAction implements ActionListener {

    public FilterSortAction() {
        putValue(NAME, "Filter & Sort");
        putValue(SHORT_DESCRIPTION, "Filter & Sort table");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity entity= (Entity) ((RightTopPanel) MainFrame.getInstance().getTopTab().getSelectedComponent()).getEntity();
        FilterSortDialog filterSortDialog=new FilterSortDialog(entity);
        filterSortDialog.setVisible(true);
    }
}
