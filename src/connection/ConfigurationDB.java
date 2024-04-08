package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigurationDB {
    public static Connection connection;

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection was closed successfully");
            } catch (SQLException e) {
                System.err.println("Connection failed. " + e.getMessage());
            }
        }
    }

    public static Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://u6zq4uavbemrl85c:6ha2PAN6Jy2R3fvZvwsA@bswxnhlsppskpmbi2yly-mysql.services.clever-cloud.com:3306/bswxnhlsppskpmbi2yly";
            String user = "u6zq4uavbemrl85c";
            String password = "6ha2PAN6Jy2R3fvZvwsA";

            connection = (Connection) DriverManager.getConnection(url, user, password);

            System.out.println("Connection was successful");
        } catch (ClassNotFoundException e) {
            System.err.println("Error. Driver not found");
        } catch (SQLException e) {
            System.err.println("Connection failed. " + e.getMessage());
        }
        return connection;
    }
}
