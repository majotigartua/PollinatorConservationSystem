package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import pollinatorconservation.model.pojo.Pollinator;

/**
 *
 * @author sebtr
 */
public class PollinatorDAOTest {
    
    /**
     * Test of registerFloweringPlant method, of class FloweringPlantDAO.
     */
    @Test
    public void succeededTestRegisterPollinator() throws SQLException {
        Pollinator pollinator = new Pollinator();
        pollinator.setDescription("");
        pollinator.setGenericName("");
        pollinator.setScientificName("");
        pollinator.getFamily().setIdFamily(0);
        
        PollinatorDAO.registerPollinator(pollinator);
        
        assertEquals(this, this); // To be determined.
    }
    
    @Test
    public void succeededTestGetPollinator() throws SQLException {
        ArrayList<Pollinator> pollinators = null;
        
        pollinators = PollinatorDAO.getPollinators();
        assertEquals(!pollinators.isEmpty(), true);
    }
    
    @Test
    public void failedTestGetPollinator() throws SQLException {
        ArrayList<Pollinator> pollinators = null;
        
        pollinators = PollinatorDAO.getPollinators();
        pollinators.clear();
        
        assertEquals(pollinators.isEmpty(), true);
    }
}
