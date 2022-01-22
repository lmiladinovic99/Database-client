package actions;

import resource.implementation.Entity;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteDialogAction implements ActionListener {
    private Entity entity;
    private List<JTextField> textFields;
    private Map<String, String> map;

    public DeleteDialogAction(Entity entity, List<JTextField> textFieldsList) {
        this.entity=entity;
        this.textFields=textFieldsList;
        this.map=new HashMap<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<textFields.size();i++) {
                map.put(textFields.get(i).getName(), textFields.get(i).getText());
        }

        MainFrame.getInstance().getAppCore().deleteRow(map, entity);
    }
}
