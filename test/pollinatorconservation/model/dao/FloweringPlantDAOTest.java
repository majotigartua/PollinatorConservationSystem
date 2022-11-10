package pollinatorconservation.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;

public class FloweringPlantDAOTest {

    static FloweringPlant floweringPlant;
    static ArrayList<FloweringPlant> floweringPlants;

    @BeforeClass
    public static void setUp() {
        setUpFloweringPlant();
        setUpFloweringPlants();
    }

    private static void setUpFloweringPlant() {
        floweringPlant = new FloweringPlant();
        floweringPlant.setScientificName("Magnolia grandiflora");
        floweringPlant.setGenericName("Magnolia");
        floweringPlant.setDescription("Es un árbol perennifolio que puede llegar a más de 30 m de altura. "
                + "Forma una densa copa de ligeramente piramidal. Tronco gris o marrón claro con corteza lisa de joven tornándose estriada al envejecer. "
                + "Las hojas alternas son simples, de elípticas a oblongo-ovadas o ampliamente ovadas y margen entero. "
                + "Miden 10-20 cm de longitud por 7-10 cm de ancho, con los márgenes enteros, de color verde brillante oscuro y textura coriácea, con haz glabro y envés algo pubescente; "
                + "pecíolo con una lanosidad corta, como aterciopelada rojiza o blanca, al igual que las yemas y las ramas jóvenes. "
                + "Las fragantes flores son hermafroditas, solitarias, de 15 a 30 cm de diámetro con 3 sépalos petaloides y 6 pétalos —pueden ser hasta 12— ovalados, de textura cerúlea; "
                + "con numerosos estambres. El fruto es un agregado de múltiples frutillos (folículos) con 1 o 2 semillas de envoltura rojiza (arilo) cada uno, en forma de piña alargada con textura leñosa.");
        Family family = new Family();
        family.setIdFamily(50);
        floweringPlant.setFamily(family);
    }

    private static void setUpFloweringPlants() {
        floweringPlants = new ArrayList<>();
        FloweringPlant firstFloweringPlant = new FloweringPlant();
        firstFloweringPlant.setScientificName("Amborella trichopoda");
        firstFloweringPlant.setGenericName("Amborella");
        floweringPlants.add(firstFloweringPlant);
        FloweringPlant secondFloweringPlant = new FloweringPlant();
        secondFloweringPlant.setScientificName("Magnolia grandiflora");
        secondFloweringPlant.setGenericName("Magnolia");
        floweringPlants.add(secondFloweringPlant);
    }

    @Test
    public void editFloweringPlantTest() throws SQLException {
        int responseCode = FloweringPlantDAO.editFloweringPlant(floweringPlant);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    @Test
    public void deleteFloweringPlantTest() throws SQLException {
        String scientificName = floweringPlant.getScientificName();
        int responseCode = FloweringPlantDAO.deleteFloweringPlant(scientificName);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    @Test
    public void getFloweringPlantTest() throws SQLException {
        String scientificName = floweringPlant.getScientificName();
        FloweringPlant test = FloweringPlantDAO.getFloweringPlant(scientificName);
        assertEquals(test, floweringPlant);
    }

    @Test
    public void getFloweringPlantsTest() throws SQLException {
        ArrayList<FloweringPlant> test = FloweringPlantDAO.getFloweringPlants();
        assertTrue(test.containsAll(floweringPlants));
    }

    @Test
    public void registerFloweringPlantTest() throws SQLException {
        int responseCode = FloweringPlantDAO.registerFloweringPlant(floweringPlant);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

}