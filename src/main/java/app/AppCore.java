package app;

import database.Database;
import database.DatabaseImplementation;
import database.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import model.TableModel;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import utils.Constants;

import java.util.List;
import java.util.Map;

public class AppCore extends PublisherImplementation {
    private Database database;
    private Settings settings;
    private TableModel tableModel1;
    private TableModel tableModel2;

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLrepository(this.settings));
        tableModel1 = new TableModel();
        tableModel2 = new TableModel();
    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImplementation;
    }


    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
    }

    public void addRow(Map<String, String> map, Entity entity) {
        this.database.addRow(map, entity);
        this.notifySubscribers(new Notification((NotificationCode.DATA_UPDATED), entity));
    }

    public void updateRow(Map<String, String> map, Entity entity, String wherePK)
    {
        this.database.updateRow(map, entity, wherePK);
        this.notifySubscribers(new Notification((NotificationCode.DATA_UPDATED),entity));
    }

    public void deleteRow(Map<String, String> map, Entity entity) {
        this.database.deleteRow(map, entity);
        this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, entity));
    }

    public void filterSortTable(String tableName, List<String> filter, Map<String, String> sort) {
        tableModel1.setRows(this.database.filterSortTable(tableName, filter, sort));
    }

    public void countOrAverage(String tableName, String countOrAverage, String selectAttribute, List<String> groupBy) {
        tableModel1.setRows(this.database.countOrAverage(tableName, countOrAverage, selectAttribute, groupBy));
    }

    public void addRightPanel(Entity entity) {
        this.notifySubscribers(new Notification((NotificationCode.NEW_RIGHT_PANEL), entity));
    }

    public void readDataFromTable(String fromTable){
        tableModel1.setRows(this.database.readDataFromTable(fromTable));
    }

    public void readDataFromRelationTable(String fromTable){
        tableModel2.setRows(this.database.readDataFromTable(fromTable));
    }

    public void readRelationRows(Entity entity, Map<String, String> map) {
        tableModel2.setRows((this.database.relationRows(entity, map)));
    }

    public TableModel getTableModel1() {
        return tableModel1;
    }

    public TableModel getTableModel2() {
        return tableModel2;
    }

    public void setTableModel1(TableModel tableModel) {
        this.tableModel1 = tableModel;
    }

    public Settings getSettings() {
        return settings;
    }

    public Database getDatabase() {
        return database;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void searchRows(String filter, Entity entity, List<Attribute> attributes) {
        tableModel1.setRows(this.database.searchRows(filter,entity, attributes));
    }
}
