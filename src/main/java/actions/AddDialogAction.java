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

public class AddDialogAction extends AbstractAction implements ActionListener {
    private Entity entity;
    private Map<String, String> map;
    List<JTextField> textFields;

    public AddDialogAction(Entity entity, List<JTextField> textFields) {
        putValue(NAME, "Insert");
        putValue(SHORT_DESCRIPTION, "Insert into");

        this.textFields=textFields;
        this.entity=entity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        map=new HashMap<>();
        for(int i=0;i<textFields.size();i++) {
            map.put(textFields.get(i).getName(), textFields.get(i).getText());
            Attribute attribute = (Attribute) entity.getChildByName(textFields.get(i).getName());
            if (attributeTypeException(attribute, textFields.get(i).getText())) {
                ExceptionHandler.handle(ExceptionType.ATTRIBUTE_TYPE_ERROR, attribute);
                return;
            }
            if (attribute.getLength() < textFields.get(i).getText().length()){
                ExceptionHandler.handle(ExceptionType.ATTRIBUTE_LENGTH_ERROR, attribute);
                return;
            }
        }
        MainFrame.getInstance().getAppCore().addRow(map, entity);
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