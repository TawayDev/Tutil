package dev.taway.net.sql;

@SuppressWarnings("unused")
public class MySQLQueryBuilder {

    private StringBuilder queryBuilder;

    public MySQLQueryBuilder() {
        queryBuilder = new StringBuilder();
    }

//    SELECT-type query methods
    public MySQLQueryBuilder SELECT(String columns) {
        queryBuilder.append("SELECT ").append(columns).append(" ");
        return this;
    }

    public MySQLQueryBuilder FROM(String table) {
        queryBuilder.append("FROM `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder JOIN(String table) {
        queryBuilder.append("JOIN `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder INNER_JOIN(String table) {
        queryBuilder.append("INNER JOIN `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder LEFT_JOIN(String table) {
        queryBuilder.append("LEFT JOIN `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder RIGHT_JOIN(String table) {
        queryBuilder.append("RIGHT JOIN `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder ON(String condition) {
        queryBuilder.append("ON ").append(condition).append(" ");
        return this;
    }

    public MySQLQueryBuilder WHERE(String condition) {
        queryBuilder.append("WHERE ").append(condition).append(" ");
        return this;
    }

    public MySQLQueryBuilder ORDER_BY(String columns) {
        queryBuilder.append("ORDER BY ").append(columns).append(" ");
        return this;
    }

    public MySQLQueryBuilder LIMIT(int limit) {
        queryBuilder.append("LIMIT ").append(limit).append(" ");
        return this;
    }

//    UPDATE-type query methods
    public MySQLQueryBuilder UPDATE(String table) {
        queryBuilder.append("UPDATE `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder SET(String... columnsValues) {
        queryBuilder.append("SET ");
        appendCommaSeparated(columnsValues);
        queryBuilder.append(" ");
        return this;
    }

//    DELETE-type query method
    public MySQLQueryBuilder DELETE_FROM(String table) {
        queryBuilder.append("DELETE FROM `").append(table).append("` ");
        return this;
    }

//    INSERT-type query methods
    public MySQLQueryBuilder INSERT_INTO(String table, String... columns) {
        queryBuilder.append("INSERT INTO `").append(table).append("` (");
        appendCommaSeparated(columns);
        queryBuilder.append(") ");
        return this;
    }

    public MySQLQueryBuilder VALUES(String... values) {
        queryBuilder.append("VALUES (");
        appendCommaSeparated(values);
        queryBuilder.append(") ");
        return this;
    }

//    Table alteration methods
    public MySQLQueryBuilder CREATE_TABLE(String table, String... columns) {
        queryBuilder.append("CREATE TABLE `").append(table).append("` (");
        appendCommaSeparated(columns);
        queryBuilder.append(") ");
        return this;
    }

    public MySQLQueryBuilder ALTER_TABLE(String table) {
        queryBuilder.append("ALTER TABLE `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder ADD_COLUMN(String columnDefinition) {
        queryBuilder.append("ADD COLUMN ").append(columnDefinition).append(" ");
        return this;
    }

    public MySQLQueryBuilder DROP_COLUMN(String columnName) {
        queryBuilder.append("DROP COLUMN `").append(columnName).append("` ");
        return this;
    }

    public MySQLQueryBuilder MODIFY_COLUMN(String columnDefinition) {
        queryBuilder.append("MODIFY COLUMN ").append(columnDefinition).append(" ");
        return this;
    }

//    Table deletion method
    public MySQLQueryBuilder DROP_TABLE(String table) {
        queryBuilder.append("DROP TABLE `").append(table).append("` ");
        return this;
    }

    public MySQLQueryBuilder TRUNCATE_TABLE(String table) {
        queryBuilder.append("TRUNCATE TABLE `").append(table).append("` ");
        return this;
    }

//    Helper method to append comma-separated values.
    private void appendCommaSeparated(String... values) {
        for (int i = 0; i < values.length; i++) {
            queryBuilder.append(values[i]);
            if (i < values.length - 1) {
                queryBuilder.append(", ");
            }
        }
    }

    public MySQLQueryBuilder CUSTOM(String query) {
        queryBuilder.append(query).append(" ");
        return this;
    }

    public String build() {
        return queryBuilder.toString().trim() + ";";
    }
}
