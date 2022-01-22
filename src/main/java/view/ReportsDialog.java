package view;

import actions.OkCountAverageDialogAction;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ReportsDialog extends JDialog {
    private Entity entity;
    private JComboBox countOrAverageComboBox;
    private JComboBox columnComboBox;
    private List<String> columnName;
    private List<JCheckBox> columnCheckBox;
    private JButton okButton;

    public ReportsDialog(Entity entity) {
        this.entity = entity;
        columnCheckBox = new ArrayList<>();
        columnName = new ArrayList<>();

        this.setTitle("COUNT or AVERAGE "+entity.getName());
        this.setLayout(new GridLayout(0,2));
        this.setAlwaysOnTop(true);

        String s[] = {"COUNT", "AVERAGE"};
        countOrAverageComboBox = new JComboBox(s);

        this.add(new JLabel("COUNT OR AVERAGE:"));
        this.add(countOrAverageComboBox);

        for (int i = 0; i < entity.getChildCount(); i++) {
            Attribute attribute = (Attribute) entity.getChildAt(i);
            columnName.add(attribute.getName());
        }

        columnComboBox = new JComboBox(columnName.toArray());
        this.add(new JLabel("Select column:"));
        this.add(columnComboBox);

        this.add(new JLabel("ORDER BY:"));
        this.add(new JLabel());

        List<DBNode> attributes=entity.getChildren();
        this.setSize(new Dimension(300, (attributes.size()/2+4)*50));
        for(int i = 0; i < attributes.size(); i++) {
            Attribute currAttribute= (Attribute) attributes.get(i);
            JCheckBox jcb = new JCheckBox(currAttribute.getName());
            jcb.setName(currAttribute.getName());
            columnCheckBox.add(jcb);
            this.add(jcb);
        }

        if (attributes.size() % 2 == 1)
            this.add(new JLabel());

        okButton = new JButton("OK");
        okButton.addActionListener(new OkCountAverageDialogAction(entity, countOrAverageComboBox, columnComboBox, columnCheckBox));
        this.add(okButton);

        countOrAverageComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selected = (String) countOrAverageComboBox.getSelectedItem();
                if (selected.equals("AVERAGE")) {
                    for (int i = 0; i < columnCheckBox.size(); i++) {
                        Attribute attribute = (Attribute) entity.getChildByName(columnCheckBox.get(i).getName());
                        if (attribute.getAttributeType() == AttributeType.CHAR) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.VARCHAR) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.NVARCHAR) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.TEXT) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.DATE) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.DATETIME) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.BIT) {
                            columnCheckBox.get(i).setEnabled(false);
                        }
                        if (attribute.getAttributeType() == AttributeType.IMAGE) {
                            columnCheckBox.get(i).setEnabled(false);
                        }

                    }
                }
            }
        });
    }
}
