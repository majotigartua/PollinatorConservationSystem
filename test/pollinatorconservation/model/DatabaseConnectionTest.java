package pollinatorconservation.model;

import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pollinatorconservation.util.Constants;

public class DatabaseConnectionTest {
    
    @Test
    private void succeededTestOpen() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        assertEquals(Constants.CONNECTION_ESTABLISHED, databaseConnection.getConnectionStatus());
    }
    
    @Test
    private void failedTestOpen() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        assertEquals(Constants.CONNECTION_LOST, databaseConnection.getConnectionStatus());
    }
    
}