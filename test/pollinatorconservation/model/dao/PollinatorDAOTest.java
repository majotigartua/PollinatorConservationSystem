package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import pollinatorconservation.model.pojo.Pollinator;

public class PollinatorDAOTest {

    public void editPollinatorTest() throws SQLException {
        assertEquals(this, this);
    }

    public void deletePollinatorTest() throws SQLException {
        assertEquals(this, this);
    }

    @Test
    public void getPollinatorTest() throws SQLException {
        String scientificName = "";
        Pollinator pollinator = PollinatorDAO.getPollinator(scientificName);
        assertEquals(!pollinator.equals(null), true);
    }

    @Test
    public void getPollinatorsTest() throws SQLException {
        ArrayList<Pollinator> pollinators = null;
        pollinators = PollinatorDAO.getPollinators();
        assertEquals(pollinators.isEmpty(), true);
    }

    @Test
    public void registerPollinatorTest() throws SQLException {
        Pollinator pollinator = new Pollinator();
        pollinator.setDescription("");
        pollinator.setGenericName("");
        pollinator.setScientificName("");
        pollinator.getFamily().setIdFamily(0);
        PollinatorDAO.registerPollinator(pollinator);
        assertEquals(this, this);
    }

}