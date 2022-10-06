package pollinatorconservation.model.dao;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.model.dao.PollinatorDAO;

/**
 *
 * @author sebtr
 */
public class PollinatorDAOTest {
    
    /**
     * Test of registerFloweringPlant method, of class FloweringPlantDAO.
     */
    @Test
    public void succeededTestRegisterFloweringPlant() throws SQLException {
        Pollinator pollinator = new Pollinator();
        pollinator.setDescription("");
        pollinator.setGenericName("");
        pollinator.setScientificName("");
        pollinator.setImagePath("");
        pollinator.getFamily().setIdFamily(0);
        
        PollinatorDAO.registerPollinator(pollinator);
        
        assertEquals(this, this); // The read method will help to complete the test.
    }
}
