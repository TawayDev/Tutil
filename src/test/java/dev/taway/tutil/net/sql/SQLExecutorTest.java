package dev.taway.tutil.net.sql;

import dev.taway.tutil.RuntimeConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SQLExecutorTest {

    @Test
    @Disabled("SQL: Feature unfinished!")
    void executeQuery() throws SQLException {
//        TODO: fixme
        RuntimeConfig.SQL.URL = "jdbc:mysql://127.0.0.1:3306";
        RuntimeConfig.SQL.username = "root";
        RuntimeConfig.SQL.password = "";
        SQLExecutor sqlExecutor = new SQLExecutor();
        sqlExecutor.executeQuery(
                new MySQLQueryBuilder().CREATE_TABLE("MyTable")
                        .build()
        );
        sqlExecutor.executeQuery(
                new MySQLQueryBuilder().ALTER_TABLE("MyTable")
                        .ADD_COLUMN("test1,test2")
                        .build()
        );
        sqlExecutor.executeQuery(
                new MySQLQueryBuilder().INSERT_INTO("MyTable", "test1", "test2")
                        .VALUES("val1,val2")
                        .build()
        );
        ResultSet resultSet = sqlExecutor.executeQuery(
                new MySQLQueryBuilder().SELECT("*")
                        .FROM("MyTable")
                        .build()
        );
        while (resultSet.next()) {
            String test1 = resultSet.getString("test1");
            String test2 = resultSet.getString("test2");

            // Perform your assertions here
            assertNotNull(test1, "test1 should not be null");
            assertNotNull(test2, "test2 should not be null");

            // Example assertion
            assertEquals("val1", test1);
            assertEquals("val2", test2);
        }
    }
}