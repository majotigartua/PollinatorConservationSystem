package pollinatorconservation.model.dao;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import pollinatorconservation.model.pojo.Pollinator;

public class PollinatorDAOTest {
    
    @Test
    public void registerPollinatorSuccesfulTest() throws SQLException {
        Pollinator pollinator = new Pollinator();
        pollinator.setScientificName("");
        pollinator.setGenericName("");
        pollinator.setDescription("");
        pollinator.getFamily().setIdFamily(0);
        
        int testResult = PollinatorDAO.registerPollinator(pollinator);
        
        assertEquals(testResult, pollinatorconservation.util.Constants.CORRECT_OPERATION_CODE);
    }
}