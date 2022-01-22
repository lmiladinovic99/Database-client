package view;

import actions.ComboboxSelectAction;
import actions.ExecuteSearchFilter;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SearchDialog extends JDialog {
    private Entity entity;
    private JComboBox<Attribute> cmbAttributes;
    private JPanel otherOptions;
    private JLabel lbQuery;
    private JButton btnSearch;
    private JButton btnReset;
    private List<Attribute> attributesToSelect;

    public SearchDialog(Entity entity) {
        this.attributesToSelect=new ArrayList<>();
        this.entity=entity;
        this.lbQuery=new JLabel();
        this.btnSearch=new JButton("Search");
        this.btnReset=new JButton("Reset");
        this.otherOptions=new JPanel(new GridLayout(1,5));
        this.setTitle("Search "+entity.getName());
        this.setLayout(new GridLayout(0,1));
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);

        List<Attribute> attributes=removeDateTimeAttributes(entity.getChildren());
        this.setSize(new Dimension(400, 300));
        cmbAttributes=new JComboBox(attributes.toArray());
        cmbAttributes.setSelectedIndex(-1);
        cmbAttributes.addItemListener(new ComboboxSelectAction(this));
        this.add(cmbAttributes);
        this.add(otherOptions);
        this.add(lbQuery);
        this.add(btnSearch);
        this.add(btnReset);

        btnSearch.addActionListener(new ExecuteSearchFilter(lbQuery,getEntity(),attributesToSelect));
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbQuery.setText("");
            }
        });
    }

    private List<Attribute> removeDateTimeAttributes(List<DBNode> children) {
        List<Attribute> attributes=new ArrayList<>();
        for(DBNode currNode:children) {
            Attribute currAttribute=(Attribute) currNode;
            if(currAttribute.getAttributeType()!= AttributeType.DATETIME && currAttribute.getAttributeType()!= AttributeType.DATE)
                attributes.add(currAttribute);
        }

        return attributes;
    }

    public JPanel getOtherOptions() {
        return this.otherOptions;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public JComboBox<Attribute> getCmbAttributes() {
        return this.cmbAttributes;
    }

    public void appendQuery(String s) {
        String text=lbQuery.getText();
        lbQuery.setText(text+s);
    }

    public JLabel getLbQuery() {
        return lbQuery;
    }

    public void addAttribute(Attribute a) {
        if(!attributesToSelect.contains(a))
            attributesToSelect.add(a);
    }
}