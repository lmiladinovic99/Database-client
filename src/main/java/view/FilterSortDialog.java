package view;

import actions.AddDialogAction;
import actions.FilterSortAction;
import actions.FilterSortDialogAction;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilterSortDialog extends JDialog {
    private Entity entity;
    private List<JCheckBox> filterCB;
    private List<JCheckBox> sortCB;
    private List<JComboBox> sortComboBox;
    private JButton filterSortButton;

    public FilterSortDialog(Entity entity) {
        this.entity=entity;
        filterCB=new ArrayList<>();
        sortCB = new ArrayList<>();
        sortComboBox = new ArrayList<>();

        this.setTitle("Filter & Sort "+entity.getName());
        this.setLayout(new GridLayout(0,3));
        this.setAlwaysOnTop(true);

        this.add(new JLabel("Filter:"));
        this.add(new JLabel("Sort:"));
        this.add(new JLabel("Order by:"));

        List<DBNode> attributes=entity.getChildren();
        this.setSize(new Dimension(450, (attributes.size()+2)*50));
        for(int i=0;i<attributes.size();i++) {
            Attribute currAttribute= (Attribute) attributes.get(i);
            JCheckBox cbf = new JCheckBox(currAttribute.getName());
            cbf.setName(currAttribute.getName());
            filterCB.add(cbf);
            this.add(cbf);
            JCheckBox cbs = new JCheckBox(currAttribute.getName());
            cbs.setName(currAttribute.getName());
            sortCB.add(cbs);
            this.add(cbs);
            String s[] = {"ASC", "DESC"};
            JComboBox sComboBox = new JComboBox(s);
            sortComboBox.add(sComboBox);
            this.add(sComboBox);
        }

        filterSortButton=new JButton("FILTER & SORT");
        filterSortButton.addActionListener(new FilterSortDialogAction(entity, filterCB, sortCB, sortComboBox));
        this.add(filterSortButton);
    }
}
