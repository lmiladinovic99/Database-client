package view;

import actions.DeleteDialogAction;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteDialog extends JDialog {
    private Entity entity;
    private List<JTextField> textFieldsList;
    private JButton deleteButton;

    public DeleteDialog(Entity entity) {
        this.entity=entity;
        textFieldsList=new ArrayList<>();

        this.setTitle("Delete from "+entity.getName());
        this.setLayout(new GridLayout(0,2));
        this.setAlwaysOnTop(true);

        List<DBNode> primaryKeyAttributes = new ArrayList<>();
        for (int i = 0; i < entity.getChildCount(); i++) {
            Attribute attribute = (Attribute) entity.getChildAt(i);
            if (attribute.isPrimaryKey())
                primaryKeyAttributes.add(attribute);
        }
        this.setSize(new Dimension(300, (primaryKeyAttributes.size()+1)*50));
        for(int i = 0; i < primaryKeyAttributes.size(); i++) {
            Attribute currAttribute= (Attribute) primaryKeyAttributes.get(i);
            JLabel currLabel=new JLabel(currAttribute.getName(), SwingConstants.CENTER);
            this.add(currLabel);
            JTextField currTextField=new JTextField(SwingConstants.CENTER);
            currTextField.setName(currAttribute.getName());
            textFieldsList.add(currTextField);
            this.add(currTextField);
        }

        deleteButton=new JButton("DELETE");
        deleteButton.addActionListener(new DeleteDialogAction(entity, textFieldsList));
        this.add(deleteButton);
    }
}
