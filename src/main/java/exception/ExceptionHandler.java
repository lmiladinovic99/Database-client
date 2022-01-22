package exception;

import resource.implementation.Attribute;
import view.MainFrame;

import javax.swing.*;

public class ExceptionHandler {
    public static void handle(ExceptionType exceptionType, Attribute attribute) {
        if (exceptionType == ExceptionType.ATTRIBUTE_TYPE_ERROR) {
            String message = attribute.getName() + " must be a " + attribute.getAttributeType();
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.ATTRIBUTE_LENGTH_ERROR) {
            String message = "Max length of " + attribute.getName() + " is " + attribute.getLength();
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.NO_COlUMN_SELECTED_FOR_FILTER) {
            String message = "Column must be selected for FILTER";
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.NO_COLUMN_SELECTED_FOR_SORT) {
            String message = "Column must be selected for SORT";
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.NO_COLUMN_SELECTED_FOR_REPORTS) {
            String message = "Column must be selected for REPORT";
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.COLUMN_NO_NUMERIC_FOR_AVERAGE) {
            String message = attribute.getName() + " is not a NUMERIC";
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.EMPTY_QUERY) {
            String message = "There is no search filter";
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
        if (exceptionType == ExceptionType.NO_SELECTED_ROW) {
            String message = "Row must be selected for RELATIONS";
            String title = exceptionType.toString();
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title, JOptionPane.ERROR_MESSAGE);
        }
    }
}
