package database;

import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.tree.DBNode;
import resource.data.Row;

import java.util.List;
import java.util.Map;

public class DatabaseImplementation implements Database {
    private Repository repository;

    public DatabaseImplementation(Repository repository) {
        this.repository = repository;
    }

    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

    @Override
    public void addRow(Map<String, String> map, Entity entity) {
        repository.insertIntoQuery(map, entity);
    }

    @Override
    public void updateRow(Map<String, String> map, Entity entity, String wherePK) {
        repository.updateQuery(map, entity, wherePK);
    }

    @Override
    public void deleteRow(Map<String, String> map, Entity entity) {
        repository.deleteQuery(map, entity);
    }

    @Override
    public List<Row> filterSortTable(String tableName, List<String> filterAttributes, Map<String, String> map) {
        return repository.filterSort(tableName, filterAttributes, map);
    }

    @Override
    public List<Row> relationRows(Entity entity, Map<String, String> map) {
        return this.repository.relationRows(entity, map);
    }

    @Override
    public List<Row> countOrAverage(String tableName, String countOrAverage, String selectAttribute, List<String> groupBy) {
        return this.repository.countOrAverage(tableName, countOrAverage, selectAttribute, groupBy);
    }

    @Override
    public List<Row> searchRows(String filter, Entity entity, List<Attribute> attributes) {
        return this.repository.selectQueryWithFilter(filter,entity, attributes);
    }

    public Repository getRepository() {
        return repository;
    }
}
