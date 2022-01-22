# Database client

The application is designed so that it can communicate with any **MSSQL** server and creates a tree dynamically, depending on which tables are located on the server. The tree hierarchy is as follows:

*Information resource - Entity (Table) - Attribute - Constraint*

# CRUD

Provide support for adding, modifying, and deleting records contained in the database. There are buttons for Add, Update and Delete on the toolbar of the table. Adding and modifying can be done via a dialog box. Methods should be implemented using the PreparedStatement object.

# Filter

Enable support for selecting columns to be displayed in the selected table and sorting data according to specified criteria. There is a Filter & Sort button on the table toolbar. The user can select any set of columns by which to sort the data, and the order may be different for each of them.

# Relations

Display data in the table at the bottom right - relations. It is necessary to filter the data by relations, and for the selected row from the main table to display only the relevant data from the linked tables.

# Search

Implement the method for searching the table according to the given search parameters.
The search by the given criteria gives the possibility to use the special characters % and _ for VARCHAR and CHAR fields, as well as the operator =, >, < for NUMERIC and INTEGER fields. The user can connect the constructs created in this way with AND and OR operators, which results in a complex database search.

# Reports

Implement a report generation method, which allows the user to select a column whose values to count or search for an average, and columns by which to group the display. For the AVG function, it is necessary to allow the selection of only columns that display numerical values for aggregation. The results can be grouped according to an arbitrary set of columns that belong to the observed table.

# Implementation

Implementation based on JDBC concepts and Java SWING library. Mandatory use of MVC, Observer and Bridge design patterns.



![alt text](https://github.com/gojkovicmatija99/Database-project/blob/master/demo.png)
