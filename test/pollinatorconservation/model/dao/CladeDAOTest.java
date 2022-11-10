package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import pollinatorconservation.model.pojo.Clade;

public class CladeDAOTest {

    static ArrayList<Clade> clades = new ArrayList<>();
    
    @BeforeClass
    public static void setUp() {
        Clade firstClade = new Clade();
        firstClade.setIdClade(1);
        firstClade.setName("Amborellales");
        clades.add(firstClade);
        Clade secondClade = new Clade();
        secondClade.setIdClade(2);
        secondClade.setName("Nymphaeales");
        clades.add(secondClade);
        Clade thirdClade = new Clade();
        thirdClade.setIdClade(3);
        thirdClade.setName("Austrobaileyales");
        clades.add(thirdClade);
    }

    @Test
    public void getCladesTest() throws SQLException {
        ArrayList<Clade> test = CladeDAO.getClades();
        assertTrue(test.containsAll(clades));
    }

}