package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public void succeededTestRegisterPollinatort() throws SQLException {
        Pollinator pollinator = new Pollinator();
        pollinator.setDescription("");
        pollinator.setGenericName("");
        pollinator.setScientificName("");
        pollinator.setImagePath("");
        pollinator.getFamily().setIdFamily(0);
        
        PollinatorDAO.registerPollinator(pollinator);
        
        assertEquals(this, this); // To be determined.
    }
    
    @Test
    public void succeededTestCheckPollinator() throws SQLException {
        ArrayList<Pollinator> pollinators = null;
        
        pollinators = PollinatorDAO.checkPollinators();
        assertEquals(!pollinators.isEmpty(), true);
    }
    
    @Test
    public void failedTestCheckPollinator() throws SQLException {
        ArrayList<Pollinator> pollinators = null;
        
        pollinators = PollinatorDAO.checkPollinators();
        pollinators.clear();
        
        assertEquals(pollinators.isEmpty(), true);
    }
}
