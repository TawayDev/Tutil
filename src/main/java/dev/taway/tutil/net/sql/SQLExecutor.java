package dev.taway.tutil.net.sql;

import dev.taway.tutil.RuntimeConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.*;

@NoArgsConstructor
@AllArgsConstructor
public class SQLExecutor {
    private String url = RuntimeConfig.SQL.URL;
    private String username = RuntimeConfig.SQL.username;
    private String password = RuntimeConfig.SQL.password;

    private void areCredentialsSet() throws dev.taway.tutil.exception.net.sql.SQLException {
        boolean isUrlNull = (url == null);
        boolean isUsernameNull = (username == null);
        boolean isPasswordNull = (password == null);

        if (isUrlNull || isUsernameNull || isPasswordNull) {
            String errorMessage = String.format("One or more of the following is null: %s%s%s",
                    isUrlNull ? "url " : "",
                    isUsernameNull ? "username " : "",
                    isPasswordNull ? "password " : ""
            );
            throw new dev.taway.tutil.exception.net.sql.SQLException(errorMessage);
        }
    }

    /**
     * Executes an SQL query and returns the result set. Make sure to sanitise it first to avoid any SQL injection attacks.
     *
     * @see #executePreparedQuery(PreparedStatement)
     * @param query the SQL query to be executed
     * @return the result set of the executed query
     * @throws RuntimeException if there is an SQL exception while executing the query
     */
    public ResultSet executeQuery(String query) {
        try {
            areCredentialsSet();
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException | dev.taway.tutil.exception.net.sql.SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Executes a prepared SQL query and returns the result set. The statement must be prepared beforehand but is generally more safe than executing a query without preparation.
     *
     * @param preparedStatement the prepared statement containing the query to be executed
     * @return the result set of the executed query
     * @throws RuntimeException if there is an SQL exception while executing the query
     */
    public ResultSet executePreparedQuery(PreparedStatement preparedStatement) {
        try {
            areCredentialsSet();
            return preparedStatement.executeQuery();
        } catch (SQLException | dev.taway.tutil.exception.net.sql.SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
