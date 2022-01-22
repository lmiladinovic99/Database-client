package actions;

import exception.ExceptionHandler;
import exception.ExceptionType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterSortDialogAction implements ActionListener {
    private Entity entity;
    private List<JCheckBox> filterCB;
    private List<JCheckBox> sortCB;
    private List<JComboBox> sortComboBox;
    private List<String> filter;
    private Map<String, String> sort;

    public FilterSortDialogAction(Entity entity, List<JCheckBox> filterCB, List<JCheckBox> sortCB, List<JComboBox> sortComboBox) {
        this.entity = entity;
        this.filterCB = filterCB;
        this.sortCB = sortCB;
        this.sortComboBox = sortComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        filter = new ArrayList<>();
        sort = new HashMap<>();
        for (int i = 0; i < filterCB.size(); i++) {
            if (filterCB.get(i).isSelected()) {
                filter.add(filterCB.get(i).getName());
                //System.out.println(filterCB.get(i).getName());
            }
        }
        for (int i = 0; i < sortCB.size(); i++) {
            if (sortCB.get(i).isSelected()) {
                sort.put(sortCB.get(i).getName(), (String) sortComboBox.get(i).getSelectedItem());
            }
        }
        if (filter.size() == 0){
            ExceptionHandler.handle(ExceptionType.NO_COlUMN_SELECTED_FOR_FILTER, (Attribute) entity.getChildAt(0));
            return;
        }
        if (sort.size() == 0){
            ExceptionHandler.handle(ExceptionType.NO_COLUMN_SELECTED_FOR_SORT, (Attribute) entity.getChildAt(0));
            return;
        }
        MainFrame.getInstance().getAppCore().filterSortTable(entity.getName(), filter, sort);
    }
}
