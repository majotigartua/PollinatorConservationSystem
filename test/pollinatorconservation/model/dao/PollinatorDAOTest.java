package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.util.Constants;

public class PollinatorDAOTest {

    static Pollinator pollinator;
    static ArrayList<Pollinator> pollinators;

    @BeforeClass
    public static void setUp() {
        setUpPollinator();
        setUpPollinators();
    }

    private static void setUpPollinator() {
        pollinator = new Pollinator();
        pollinator.setScientificName("Lithurgus rubricatus");
        pollinator.setGenericName("Abeja megaquílida lithurgus rubricatus");
        pollinator.setDescription("De 7 a 18 mm. de largo. "
                + "Son abejas en las que el polen es transportado en una escopa ubicada en el abdomen, a diferencia de la mayoría de las abejas que tienen ese órgano en las patas posteriores. "
                + "Varias abejas parásitas o abejas cucos de esta familia no colectan polen y no tienen escopa, géneros Stelis y Coelioxys. Tienen una cabeza robusta. "
                + "Otra característica es que el labro o labio superior es rectangular, más largo que ancho.");
        Family family = new Family();
        family.setIdFamily(11);
        pollinator.setFamily(family);
    }

    private static void setUpPollinators() {
        pollinators = new ArrayList<>();
        Pollinator firstPollinator = new Pollinator();
        firstPollinator.setScientificName("Amborella trichopoda");
        firstPollinator.setGenericName("Amborella");
        pollinators.add(firstPollinator);
        Pollinator secondPollinator = new Pollinator();
        secondPollinator.setScientificName("Ptychoptera contaminata");
        secondPollinator.setGenericName("Mosca fantasma de la grulla");
        pollinators.add(secondPollinator);
    }

    @Test
    public void editPollinatorTest() throws SQLException {
        int responseCode = PollinatorDAO.editPollinator(pollinator);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    @Test
    public void deletePollinatorTest() throws SQLException {
        String scientificName = pollinator.getScientificName();
        int responseCode = PollinatorDAO.deletePollinator(scientificName);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    @Test
    public void getPollinatorTest() throws SQLException {
        String scientificName = pollinator.getScientificName();
        Pollinator test = PollinatorDAO.getPollinator(scientificName);
        assertEquals(test, pollinator);
    }

    @Test
    public void getPollinatorsTest() throws SQLException {
        ArrayList<Pollinator> test = PollinatorDAO.getPollinators();
        assertTrue(test.containsAll(pollinators));
    }

    @Test
    public void registerPollinatorTest() throws SQLException {
        int responseCode = PollinatorDAO.registerPollinator(pollinator);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

}