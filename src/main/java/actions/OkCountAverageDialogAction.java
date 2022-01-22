package actions;

import exception.ExceptionHandler;
import exception.ExceptionType;
import resource.enums.AttributeType;
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

public class OkCountAverageDialogAction implements ActionListener {
    private Entity entity;
    private List<JCheckBox> columnCheckBox;
    private JComboBox countOrAverage;
    private JComboBox selectedColumn;
    private List<String> groupBy;

    public OkCountAverageDialogAction(Entity entity, JComboBox countOrAverage, JComboBox selectedColumn, List<JCheckBox> columnCheckBox) {
        this.entity = entity;
        this.countOrAverage = countOrAverage;
        this.selectedColumn = selectedColumn;
        this.columnCheckBox = columnCheckBox;
        this.countOrAverage = countOrAverage;
        this.selectedColumn = selectedColumn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        groupBy = new ArrayList<>();
        for (int i = 0; i < columnCheckBox.size(); i++) {
            if (columnCheckBox.get(i).isSelected())
                groupBy.add(columnCheckBox.get(i).getName());
        }
        String countOrAverageStr = (String) countOrAverage.getSelectedItem();
        Attribute attribute = (Attribute) entity.getChildByName((String)selectedColumn.getSelectedItem());
        if ((attribute.getAttributeType() == AttributeType.CHAR || attribute.getAttributeType() == AttributeType.VARCHAR ||
                attribute.getAttributeType() == AttributeType.NVARCHAR || attribute.getAttributeType() == AttributeType.TEXT ||
                attribute.getAttributeType() == AttributeType.DATE || attribute.getAttributeType() == AttributeType.DATETIME ||
                attribute.getAttributeType() == AttributeType.BIT || attribute.getAttributeType() == AttributeType.IMAGE) &&
                countOrAverageStr.equals("AVERAGE"))
        {
            ExceptionHandler.handle(ExceptionType.COLUMN_NO_NUMERIC_FOR_AVERAGE, attribute);
            return;
        }
        if (groupBy.size() == 0) {
            ExceptionHandler.handle(ExceptionType.NO_COLUMN_SELECTED_FOR_REPORTS, (Attribute) entity.getChildAt(0));
            return;
        }
        MainFrame.getInstance().getAppCore().countOrAverage(entity.getName(), (String)countOrAverage.getSelectedItem(), (String)selectedColumn.getSelectedItem(), groupBy);
    }
}
