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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDialogAction extends AbstractAction implements ActionListener {
    private Entity entity;
    private Map<String, String> map;
    private List<JTextField> textFields;
    private JTextField wherePKequalsTextField;

    public UpdateDialogAction(Entity entity, List<JTextField> textFieldsList, JTextField wherePKequalsTextField) {
        putValue(NAME, "Update");
        putValue(SHORT_DESCRIPTION, "Update column");

        this.entity=entity;
        this.textFields=textFieldsList;
        this.map=new HashMap<>();
        this.wherePKequalsTextField=wherePKequalsTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Attribute primaryKey = null;
        for(int i=0;i<textFields.size();i++) {
            if(!textFields.get(i).getText().equals("")) {
                map.put(textFields.get(i).getName(), textFields.get(i).getText());
                Attribute attribute = (Attribute) entity.getChildByName(textFields.get(i).getName());
                if (attribute.isPrimaryKey())
                    primaryKey = attribute;
                if (attributeTypeException(attribute, textFields.get(i).getText())) {
                    ExceptionHandler.handle(ExceptionType.ATTRIBUTE_TYPE_ERROR, attribute);
                    return;
                }
                if (attribute.getLength() < textFields.get(i).getText().length()) {
                    ExceptionHandler.handle(ExceptionType.ATTRIBUTE_LENGTH_ERROR, attribute);
                    return;
                }
            }
        }
        String wherePK=wherePKequalsTextField.getText();
        if (attributeTypeException(primaryKey, wherePK)) {
            ExceptionHandler.handle(ExceptionType.ATTRIBUTE_TYPE_ERROR, primaryKey);
            return;
        }
        MainFrame.getInstance().getAppCore().updateRow(map, entity, wherePK);
    }

    private boolean attributeTypeException(Attribute attribute, String text) {
        if (attribute.getAttributeType() == AttributeType.BIGINT || attribute.getAttributeType() == AttributeType.INT) {
            try{
                Integer.parseInt(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.SMALLINT){
            try{
                Short.parseShort(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.FLOAT){
            try{
                Double.parseDouble(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.REAL){
            try{
                Float.parseFloat(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.DECIMAL || attribute.getAttributeType() == AttributeType.NUMERIC){
            try{
                new BigDecimal(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.DATE){
            try{
                Date.valueOf(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.DATETIME){
            try{
                Timestamp.valueOf(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        if (attribute.getAttributeType() == AttributeType.TIME){
            try{
                Time.valueOf(text);
            }
            catch(Exception e) {
                return true;
            }
        }
        return false;
    }
}
