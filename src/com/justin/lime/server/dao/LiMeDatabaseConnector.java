package com.justin.lime.server.dao;

import java.sql.*;

import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.DEFAULT_SQL_DRIVER_NAME;

/**
 * @author Justin Lee
 */
public class LiMeDatabaseConnector {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public LiMeDatabaseConnector(String host, int port, String database, String username, String password) throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?" + "User=" + username +
                "&password=" + password + "&useUnicode=true&characterEncoding=UTF8&useSSL=true" +
                "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            Class.forName(DEFAULT_SQL_DRIVER_NAME);
            connection = DriverManager.getConnection(url);
            if (!connection.isClosed()) {
                System.out.println("Database connection Succeeded");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver loading failure");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeQuery(String sql) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            handleSqlException(e);
        }
        return resultSet;
    }

    public void closeStatement() {
        try {
            statement.close();
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    private static void handleSqlException(SQLException e) {
        System.err.println("Data.executeQuery:" + e.getMessage());
        e.printStackTrace();
        System.exit(0);
    }
}
