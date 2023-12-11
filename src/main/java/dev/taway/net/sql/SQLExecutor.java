package dev.taway.net.sql;
import dev.taway.RuntimeConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@NoArgsConstructor
@AllArgsConstructor
public class SQLExecutor {
    private String url = RuntimeConfig.SQL.URL;
    private String username = RuntimeConfig.SQL.username;
    private String password = RuntimeConfig.SQL.password;

    /**
     * Executes an SQL query. Make sure to sanitize the query first before sending it.
     * @see MySQLQueryBuilder
     */
    public ResultSet executeQuery(String query) {
        try {
            if((url == null) || (username == null) || (password == null)) throw new dev.taway.exception.net.sql.SQLException("One or more of the following is null: url, username and or password.");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException | dev.taway.exception.net.sql.SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
