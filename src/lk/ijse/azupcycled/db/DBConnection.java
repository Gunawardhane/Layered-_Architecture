package lk.ijse.upcycled.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class   DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/furniture?useSSL=false", "root", "1234");
    }

    public static DBConnection getDBConnection() throws SQLException, ClassNotFoundException {
        return (null == dbConnection) ? new DBConnection() : dbConnection;
    }
    public Connection getConnection() {
        return connection;
    }
}

