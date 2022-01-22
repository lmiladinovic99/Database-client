package actions;

import app.Main;
import exception.ExceptionHandler;
import exception.ExceptionType;
import model.TableModel;
import resource.data.Row;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import view.MainFrame;
import view.RightBottomPanel;
import view.RightTopPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class RelationsAction extends AbstractAction implements ActionListener {

    public RelationsAction() {
        putValue(NAME, "Relations");
        putValue(SHORT_DESCRIPTION, "Relation rows");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RightTopPanel topPanel = (RightTopPanel)MainFrame.getInstance().getTopTab().getSelectedComponent();
        Entity entity = (Entity) topPanel.getEntity();
        JTable table = topPanel.getjTable();
        TableModel tableModel = (TableModel) table.getModel();
        if (table.getSelectedRow() == -1) {
            ExceptionHandler.handle(ExceptionType.NO_SELECTED_ROW, null);
            return;
        }
        int rowInt = table.getSelectedRow();
        Row row = tableModel.getRows().get(rowInt);
        Map<String, Object> fields = row.getFields();
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<String, Object> entry : fields.entrySet()) {
            Attribute attribute = (Attribute) entity.getChildByName(entry.getKey());
            if (attribute != null) {
                if (attribute.isForeignKey()) {
                    map.put(attribute.getName(), (String) entry.getValue());
                }
            }
        }
        if (MainFrame.getInstance().getBottomTab().getSelectedComponent() == null)
            return;
        RightBottomPanel bottomPanel = (RightBottomPanel)MainFrame.getInstance().getBottomTab().getSelectedComponent();
        Entity relationEntity = (Entity)bottomPanel.getEntity();
        MainFrame.getInstance().getAppCore().readRelationRows(relationEntity, map);
    }
}
