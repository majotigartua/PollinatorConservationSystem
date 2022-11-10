package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import pollinatorconservation.model.pojo.Clade;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.Order;

public class FamilyDAOTest {

    static Clade clade;
    static ArrayList<Family> familiesByClade = new ArrayList<>();
    static Order order;
    static ArrayList<Family> familiesByOrder = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        setFamiliesByClade();
        setFamiliesByOrder();
    }

    private static void setFamiliesByClade() {
        clade = new Clade();
        clade.setIdClade(2);
        clade.setName("Nymphaeales");
        Family firstFamily = new Family();
        firstFamily.setIdFamily(40);
        firstFamily.setName("Hydatellaceae");
        firstFamily.setClade(clade);
        Family secondFamily = new Family();
        secondFamily.setIdFamily(41);
        secondFamily.setName("Cabombaceae");
        secondFamily.setClade(clade);
        Family thirdFamily = new Family();
        thirdFamily.setIdFamily(42);
        thirdFamily.setName("Nymphaeaceae");
        thirdFamily.setClade(clade);
    }

    private static void setFamiliesByOrder() {
        order = new Order();
        order.setIdOrder(1);
        order.setName("Hymenoptera");
        Family firstFamily = new Family();
        firstFamily.setIdFamily(9);
        firstFamily.setName("Xiphydrioidea");
        firstFamily.setOrder(order);
        Family secondFamily = new Family();
        secondFamily.setIdFamily(10);
        secondFamily.setName("Orussoidea");
        secondFamily.setOrder(order);
        Family thirdFamily = new Family();
        thirdFamily.setIdFamily(11);
        thirdFamily.setName("Apocrita");
        thirdFamily.setOrder(order);
    }

    @Test
    public void getFamiliesByCladeTest() throws SQLException {
        ArrayList<Family> test = FamilyDAO.getFamiliesByClade(clade);
        assertTrue(test.containsAll(familiesByClade));
    }

    @Test
    public void getFamiliesByOrderTest() throws SQLException {
        ArrayList<Family> test = FamilyDAO.getFamiliesByOrder(order);
        assertTrue(test.containsAll(familiesByOrder));
    }

}