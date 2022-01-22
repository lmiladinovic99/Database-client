package actions;

import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import view.SearchDialog;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboboxSelectAction implements ItemListener {
    private JPanel otherOpstions;
    private SearchDialog searchDialog;

    private JComboBox<Attribute> cmbAttributes;
    private JComboBox<String> cmbOperations;
    private JTextField textField;
    private JButton btnAND;
    private JButton btnOR;

    public ComboboxSelectAction(SearchDialog searchDialog) {
        this.searchDialog = searchDialog;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED) {
            this.otherOpstions = searchDialog.getOtherOptions();
            this.cmbAttributes = searchDialog.getCmbAttributes();
            this.textField = new JTextField();
            this.btnAND = new JButton("AND");
            this.btnOR = new JButton("OR");

            Attribute attribute = (Attribute) cmbAttributes.getSelectedItem();
            AttributeType attributeType = attribute.getAttributeType();
            this.textField.setText("");
            otherOpstions.removeAll();
            otherOpstions.repaint();

            String[] operations;
            if (attributeType == AttributeType.VARCHAR || attributeType == AttributeType.CHAR || attributeType == AttributeType.TEXT || attributeType == AttributeType.NVARCHAR) {
                operations = new String[]{"Starts with", "Ends with", "Contains", "Equals"};
            } else {
                operations = new String[]{"=", "<", ">"};
            }

            cmbOperations = new JComboBox<>(operations);
            otherOpstions.add(cmbOperations);
            otherOpstions.add(textField);
            otherOpstions.add(btnAND);
            otherOpstions.add(btnOR);
            btnAND.addActionListener(new AddToQueryAction(searchDialog, attribute, cmbOperations, textField, "AND"));
            btnOR.addActionListener(new AddToQueryAction(searchDialog, attribute, cmbOperations, textField, "OR"));


            otherOpstions.revalidate();
        }

    }
}
