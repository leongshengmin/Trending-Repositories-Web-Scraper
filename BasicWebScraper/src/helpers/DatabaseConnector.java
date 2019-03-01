package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {

    private Connection dbConnection;

    public DatabaseConnector(String connectionUrl) {
        // todo change to env
        try {
            dbConnection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertUpdate(String query) throws SQLException {
        return dbConnection.createStatement().executeUpdate(query);
    }

    public ResultSet selectQuery(String query) throws SQLException {
        return dbConnection.createStatement().executeQuery(query);
    }
}
