package actions;

import exception.ExceptionHandler;
import exception.ExceptionType;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import view.SearchDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddToQueryAction implements ActionListener {
    private SearchDialog searchDialog;
    private JComboBox cmbOperations;
    private Attribute attribute;
    private JTextField textField;
    private String connector;

    public AddToQueryAction(SearchDialog searchDialog, Attribute attribute, JComboBox<String> cmbOperations, JTextField textField, String connector) {
        this.searchDialog=searchDialog;
        this.connector=connector;
        this.attribute=attribute;
        this.textField=textField;
        this.cmbOperations=cmbOperations;

    }

    private String getOperationFromString(String string, String criterium) {
        if(string.equals("Ends with"))
            return "LIKE '%"+criterium+"'";
        if(string.equals("Starts with"))
            return "LIKE '"+criterium+"%'";
        if(string.equals("Contains"))
            return "LIKE '%"+criterium+"%'";
        if(string.equals("Equals"))
            return "LIKE '"+criterium+"'";
        return null;
    }

    Boolean isNumber(String number) {
        try{
            Integer.parseInt(number);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(textField.getText().equals(""))
            return;

        String newQuery="";
        searchDialog.addAttribute(attribute);
        if(!searchDialog.getLbQuery().getText().equals(""))
            newQuery=connector+" ";
        newQuery+= attribute.getName()+" ";
        AttributeType attributeType=attribute.getAttributeType();
        if (attributeType == AttributeType.VARCHAR || attributeType == AttributeType.CHAR || attributeType == AttributeType.TEXT || attributeType == AttributeType.NVARCHAR) {
            newQuery+=getOperationFromString(cmbOperations.getSelectedItem().toString(), textField.getText())+" ";
        } else {
            newQuery+=cmbOperations.getSelectedItem().toString()+" ";
            if(isNumber(textField.getText()))
                newQuery+=textField.getText()+" ";
            else {
                ExceptionHandler.handle(ExceptionType.ATTRIBUTE_TYPE_ERROR, attribute);
                return;
            }
        }

        searchDialog.appendQuery(newQuery);
        searchDialog.revalidate();
    }
}
