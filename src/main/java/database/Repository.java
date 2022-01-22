package database;

import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;
import resource.data.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Repository {
    DBNode getSchema();
    List<Row> get(String from);
    void insertIntoQuery(Map<String, String> map, Entity entity);
    void updateQuery(Map<String, String> map, Entity entity, String wherePK);
    void deleteQuery(Map<String, String> map, Entity entity);
    List<Row> filterSort(String from, List<String> filterAttributes, Map<String, String> map);
    List<Row> relationRows(Entity entity, Map<String, String> map);
    List<Row> countOrAverage(String from, String countOrAverage, String selectAttribute, List<String> groupBy);
    List<Row> selectQueryWithFilter(String filter, Entity entity, List<Attribute> attributes);
}
