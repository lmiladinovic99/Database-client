package database;

import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;
import resource.data.Row;

import java.util.List;
import java.util.Map;

public interface Database {
    DBNode loadResource();
    List<Row> readDataFromTable(String tableName);
    void addRow(Map<String, String> map, Entity entity);
    void updateRow(Map<String, String> map, Entity entity, String wherePK);
    void deleteRow(Map<String, String> map, Entity entity);
    List<Row> filterSortTable(String tableName, List<String> filterAttributes, Map<String, String> map);
    List<Row> relationRows(Entity entity, Map<String, String> map);
    List<Row> countOrAverage(String tableName, String countOrAverage, String selectAttribute, List<String> groupBy);
    List<Row> searchRows(String filter, Entity entity, List<Attribute> attributes);
}
