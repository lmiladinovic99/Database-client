package database;

import database.settings.Settings;
import resource.tree.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MSSQLrepository implements Repository {
    private Connection connection;
    private Settings settings;

    public MSSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException {
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String ip = (String) settings.getParameter("mssql_ip");
            String database = (String) settings.getParameter("mssql_database");
            String username = (String) settings.getParameter("mssql_username");
            String password = (String) settings.getParameter("mssql_password");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ ip +"/"+ database, username, password);
        }
        catch(SQLException sqle) {
            System.err.println("Couldn't connect to server!");
            System.exit(3);
        }

    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection=null;
        }
    }

    @Override
    public DBNode getSchema() {
        try {
            this.initConnection();

            DatabaseMetaData metaData=connection.getMetaData();
            InformationResource ir=new InformationResource("tim_31_bp2020");

            String tableType[]={"TABLE"};
            ResultSet tables=metaData.getTables(connection.getCatalog(), null, null, tableType);

            while(tables.next()) {
                String tableName=tables.getString("TABLE_NAME");
                Entity newTable =new Entity(tableName, ir);
                if(tableName.startsWith("trace"))
                    continue;
                ir.addChild(newTable);

                ResultSet columns= metaData.getColumns(connection.getCatalog(), null, tableName, null);
                while(columns.next()) {
                    String columnsName=columns.getString("COLUMN_NAME");
                    String columnType=columns.getString("TYPE_NAME");
                    int columnSize=Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute=new Attribute(columnsName, newTable, AttributeType.valueOf((columnType.toUpperCase())), columnSize);
                    newTable.addChild(attribute);

                    String isNullable=columns.getString("IS_NULLABLE");
                    if(isNullable.equals("NO")) {
                        AttributeConstraint attributeConstraint=new AttributeConstraint("NOT NULL",attribute, ConstraintType.NOT_NULL);
                        attribute.addChild(attributeConstraint);
                    }

                    String defaultValue=columns.getString("COLUMN_DEF");
                    if(defaultValue!=null){
                        AttributeConstraint attributeConstraint=new AttributeConstraint(defaultValue, attribute, ConstraintType.DEFAULT_VALUE);
                        attribute.addChild(attributeConstraint);
                    }

                    ResultSet primaryKeys=metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                    while(primaryKeys.next()) {
                        if(columnsName.equals(primaryKeys.getString("COLUMN_NAME"))) {
                            AttributeConstraint attributeConstraint=new AttributeConstraint("PRIMARY KEY",attribute,ConstraintType.PRIMARY_KEY);
                            attribute.addChild(attributeConstraint);
                        }
                    }

                    ResultSet foreignKeys=metaData.getImportedKeys(connection.getCatalog(), null, tableName);
                    while(foreignKeys.next()) {
                        if(columnsName.equals(foreignKeys.getString("FKCOLUMN_NAME"))) {
                            AttributeConstraint attributeConstraint=new AttributeConstraint("FOREIGN KEY",attribute,ConstraintType.FOREIGN_KEY);
                            attribute.addChild(attributeConstraint);
                            newTable.addRelationTable(foreignKeys.getString("PKTABLE_NAME"));
                        }
                    }
                }
            }

            return ir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public List<Row> get(String from) {
        List<Row> rows=new ArrayList<>();

        try {
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Row row=new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData=rs.getMetaData();
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public void insertIntoQuery(Map<String, String> map, Entity entity) {
        try {
            this.initConnection();

            String query="INSERT INTO "+entity.getName()+" (";
            String values="";
            String attributes="";
            for(Map.Entry<String, String> entry : map.entrySet()) {
                attributes+=entry.getKey()+",";
                values+="?,";
            }

            attributes=attributes.substring(0, attributes.length()-1);
            values=values.substring(0,values.length()-1);

            query+=attributes+") VALUES ("+values+")";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            int i=1;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                setValue(preparedStatement, entity, entry.getKey(), entry.getValue(), i++);
            }
            preparedStatement.execute();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }
    }

    @Override
    public void updateQuery(Map<String, String> map, Entity entity, String wherePK) {
        try {
            initConnection();

            String query="UPDATE "+entity.getName()+" SET ";
            String update="";

            for(Map.Entry<String, String> entry : map.entrySet()) {
                update+=entry.getKey()+" =?,";
            }

            update=update.substring(0,update.length()-1);
            String pkColumn=getPrimaryKeyColumn(entity);
            query+=update+" WHERE "+pkColumn+"=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            int i=1;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                setValue(preparedStatement, entity, entry.getKey(), entry.getValue(), i++);
            }
            setValue(preparedStatement, entity, pkColumn, wherePK, i);
            preparedStatement.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public void deleteQuery(Map<String, String> map, Entity entity) {
        try {
            initConnection();

            String query="DELETE FROM "+entity.getName()+" WHERE " ;
            String delete="";

            for(Map.Entry<String, String> entry : map.entrySet()) {
                delete+=entry.getKey()+" =? AND";
            }

            delete=delete.substring(0,delete.length()-3);
            query+=delete;
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            int i=1;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                setValue(preparedStatement, entity, entry.getKey(), entry.getValue(), i++);
            }
            preparedStatement.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public List<Row> filterSort(String from, List<String> filterAttributes, Map<String, String> map) {
        List<Row> rows=new ArrayList<>();

        try {
            this.initConnection();

            String query = "SELECT ";
            for (int i = 0; i < filterAttributes.size()-1; i++) {
                query += filterAttributes.get(i) + ", ";
            }
            query += filterAttributes.get(filterAttributes.size()-1) + " FROM " + from + " ORDER BY ";
            for(Map.Entry<String, String> entry : map.entrySet()) {
                query += entry.getKey() + " " + entry.getValue() + ", ";
            }
            query = query.substring(0, query.length()-2);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Row row=new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData=rs.getMetaData();
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public List<Row> relationRows(Entity entity, Map<String, String> map) {
        List<Row> rows=new ArrayList<>();

        try {
            this.initConnection();

            String query = "SELECT * FROM " + entity.getName() + " WHERE ";
            for(Map.Entry<String, String> entry : map.entrySet()) {
                Attribute attribute = (Attribute) entity.getChildByName(entry.getKey());
                if (attribute != null) {
                    if (attribute.getAttributeType() == AttributeType.CHAR ||
                            attribute.getAttributeType() == AttributeType.VARCHAR ||
                            attribute.getAttributeType() == AttributeType.NVARCHAR ||
                            attribute.getAttributeType() == AttributeType.TEXT) {
                        query += attribute.getName() + " LIKE '" + entry.getValue() + "' AND ";
                    }
                    else {
                        query += attribute.getName() + " = " + entry.getValue() + " AND ";
                    }
                }
            }
            query = query.substring(0, query.length()-4);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Row row=new Row();
                row.setName(entity.getName());

                ResultSetMetaData resultSetMetaData=rs.getMetaData();
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public List<Row> countOrAverage(String from, String countOrAverage, String selectAttribute, List<String> groupBy) {
        List<Row> rows=new ArrayList<>();

        try {
            this.initConnection();

            String query = "SELECT ";
            for (int i = 0; i < groupBy.size(); i++) {
                query += groupBy.get(i) + ", ";
            }
            if (countOrAverage.equals("COUNT")) {
                query += "COUNT(" + selectAttribute + ") AS 'COUNT(" + selectAttribute + ")' ";
            }
            else {
                query += "round(avg(" + selectAttribute + "),2) AS 'round(avg(" + selectAttribute + "),2)' ";
            }
            query += "FROM " + from + " GROUP BY ";
            for (int i = 0; i < groupBy.size()-1; i++) {
                query += groupBy.get(i) + ", ";
            }
            query += groupBy.get(groupBy.size()-1);
            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Row row=new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData=rs.getMetaData();
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public List<Row> selectQueryWithFilter(String filter, Entity entity, List<Attribute> attributes) {
        List<Row> rows=new ArrayList<>();
        try {
            initConnection();

            String query = "SELECT ";
            for(int i=0;i<attributes.size();i++)
                query+=attributes.get(i).getName()+",";

            query=query.substring(0, query.length()-1);
            query+=" FROM "+entity.getName()+" WHERE "+filter;
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Row row=new Row();
                row.setName(entity.getName());

                ResultSetMetaData resultSetMetaData=rs.getMetaData();
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return rows;
    }

    private String getPrimaryKeyColumn(Entity entity) {
        List<DBNode> attributes=entity.getChildren();
        for(DBNode attributeNode:attributes) {
            Attribute currAttribute=(Attribute) attributeNode;
            List<DBNode> constraints=currAttribute.getChildren();
            for(DBNode constraintNode:constraints) {
                AttributeConstraint currConstraint=(AttributeConstraint)constraintNode;
                if(currConstraint.getConstraintType().equals(ConstraintType.PRIMARY_KEY))
                    return currAttribute.getName();
            }
        }

        return null;
    }

    public void setValue(PreparedStatement preparedStatement, Entity entity, String columnName, String columnValue, int i) throws SQLException {
        Attribute attribute = (Attribute) entity.getChildByName(columnName);
        if (attribute.getAttributeType() == AttributeType.CHAR || attribute.getAttributeType() == AttributeType.VARCHAR ||
                attribute.getAttributeType() == AttributeType.TEXT || attribute.getAttributeType() == AttributeType.NVARCHAR) {
            preparedStatement.setString(i, columnValue);
        }
        if (attribute.getAttributeType() == AttributeType.BIGINT || attribute.getAttributeType() == AttributeType.INT) {
            preparedStatement.setInt(i, Integer.parseInt(columnValue));
        }
        if (attribute.getAttributeType() == AttributeType.SMALLINT) {
            preparedStatement.setShort(i, Short.parseShort(columnValue));
        }
        if (attribute.getAttributeType() == AttributeType.FLOAT) {
            preparedStatement.setDouble(i, Double.parseDouble(columnValue));
        }
        if (attribute.getAttributeType() == AttributeType.REAL) {
            preparedStatement.setFloat(i, Float.parseFloat(columnValue));
        }
        if (attribute.getAttributeType() == AttributeType.DECIMAL || attribute.getAttributeType() == AttributeType.NUMERIC) {
            preparedStatement.setBigDecimal(i, new BigDecimal(columnValue));
        }
        if (attribute.getAttributeType() == AttributeType.BIT) {
            boolean bool;
            if (columnValue == "1")
                bool = true;
            else bool = false;
            preparedStatement.setBoolean(i, bool);
        }
        if (attribute.getAttributeType() == AttributeType.DATE) {
            Date date = Date.valueOf(columnValue);
            preparedStatement.setDate(i, date);
        }
        if (attribute.getAttributeType() == AttributeType.DATETIME) {
            Timestamp dateTime = Timestamp.valueOf(columnValue);
            preparedStatement.setTimestamp(i, dateTime);
        }
        if (attribute.getAttributeType() == AttributeType.TIME) {
            Time time = Time.valueOf(columnValue);
            preparedStatement.setTime(i, time);
        }
    }

}
