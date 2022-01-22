package actions;

import exception.ExceptionHandler;
import exception.ExceptionType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExecuteSearchFilter implements ActionListener {
    private JLabel label;
    private Entity entity;
    private List<Attribute> attributes;

    public ExecuteSearchFilter(JLabel label, Entity entity, List<Attribute> attributes) {
        this.entity=entity;
        this.label=label;
        this.attributes=attributes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(label.getText().equals("")) {
            ExceptionHandler.handle(ExceptionType.EMPTY_QUERY,null);
            return;
        }
        MainFrame.getInstance().getAppCore().searchRows(label.getText(), entity, attributes);
    }
}
