package pollinatorconservation.model.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import pollinatorconservation.model.pojo.Role;
import pollinatorconservation.model.pojo.ScientificResearcher;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class ScientificResercherDAOTest {

    static ScientificResearcher scientificResearcher;

    @BeforeClass
    public static void setUp() throws NoSuchAlgorithmException {
        scientificResearcher = new ScientificResearcher();
        scientificResearcher.setName("Carlos Miguel");
        scientificResearcher.setPaternalSurname("Pérez");
        scientificResearcher.setMaternalSurname("Pérez");
        scientificResearcher.setUsername("carlosmpp");
        String password = Utilities.computeSHA256Hash("perezperez");
        scientificResearcher.setPassword(password);
        scientificResearcher.setProfessionalLicenseNumber(password);
        scientificResearcher.setProfessionalLicenseNumber("PEPM01092801MVZRGSA3");
        Role role = new Role();
        role.setIdRole(Constants.ID_SCIENTIFIC_RESEARCHER_ROLE);
        scientificResearcher.setRole(role);
    }


    @Test
    public void registerScientificResearcherTest() throws SQLException {
        int responseCode = ScientificResearcherDAO.registerScientificResearcher(scientificResearcher);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }
    @Test
    public void editScientificResearcherTest() throws SQLException {
        int responseCode = ScientificResearcherDAO.editScientificResearcher(scientificResearcher);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    @Test
    public void deleteScientificResearcherTest() throws SQLException {
        String username = scientificResearcher.getUsername();
        int responseCode = ScientificResearcherDAO.deleteScientificResearcher(username);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    @Test
    public void getScientificResearcherTest() throws SQLException {
        String username = scientificResearcher.getUsername();
        ScientificResearcher test = ScientificResearcherDAO.getScientificResearcher(username);
        assertEquals(test, scientificResearcher);
    }


}