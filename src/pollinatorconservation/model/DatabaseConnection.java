package pollinatorconservation.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private Connection databaseConnection;

    public Connection open() throws SQLException {
        connectToDatabase();
        return databaseConnection;
    }

    private void connectToDatabase() throws SQLException {
        try {
            Properties attributes = new Properties();
            try (FileInputStream databaseConfigurationFile = new FileInputStream(new File("src\\pollinatorconservation\\model\\DatabaseConfiguration.txt"))) {
                attributes.load(databaseConfigurationFile);
            }
            String url = attributes.getProperty("URL");
            String username = attributes.getProperty("USERNAME");
            String password = attributes.getProperty("PASSWORD");
            databaseConnection = DriverManager.getConnection(url, username, password);
        } catch (FileNotFoundException exception) {
            System.err.println(exception.fillInStackTrace());
        } catch (IOException exception) {
            System.err.println(exception.fillInStackTrace());
        }

    }

    public void close() {
        if (databaseConnection != null) {
            try {
                if (!databaseConnection.isClosed()) {
                    databaseConnection.close();
                }
            } catch (SQLException exception) {
                System.err.println(exception.fillInStackTrace());
            }
        }
    }

}
