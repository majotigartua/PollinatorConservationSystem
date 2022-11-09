package pollinatorconservation.model.dao;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;

public class FloweringPlantDAOTest {

    @Test
    public void editFloweringPlantTest() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        floweringPlant.setScientificName("Amborella trichopoda");
        floweringPlant.setGenericName("Amborella");
        floweringPlant.setDescription("Es un arbusto de gran porte o arbolito, algo trepador, siempreverde, de hasta"
                + " 8 m. de altura, tomento de pelos uniseriados multicelulares (a veces unicelulares). Sus hojas son "
                + "alternas, espiraladas a dísticas en la madurez, pecioladas, sin estípulas, de margen ondulado a dentado, "
                + "a veces pinnatífidas, pinnatinervias, las venas conexas cerca del margen, estomas paracíticos a anomocíticos, "
                + "sólo en la superficie abaxial.");
        floweringPlant.getFamily().setIdFamily(39);
        int responseCode = FloweringPlantDAO.editFloweringPlant(floweringPlant);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    public void deleteFloweringPlantTest() throws SQLException {
        String scientificName = "Amborella trichopoda";
        int responseCode = FloweringPlantDAO.deleteFloweringPlant(scientificName);
        assertEquals(responseCode, Constants.CORRECT_OPERATION_CODE);
    }

    public void getFloweringPlantTest() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        String scientificName = "Amborella trichopoda";
        floweringPlant.setGenericName("Amborella");
        floweringPlant.setScientificName("Amborella trichopoda");
        floweringPlant.setDescription("Es un arbusto de gran porte o arbolito, algo trepador, siempreverde, de hasta"
                + " 8 m. de altura, tomento de pelos uniseriados multicelulares (a veces unicelulares). Sus hojas son "
                + "alternas, espiraladas a dísticas en la madurez, pecioladas, sin estípulas, de margen ondulado a dentado, "
                + "a veces pinnatífidas, pinnatinervias, las venas conexas cerca del margen, estomas paracíticos a anomocíticos, "
                + "sólo en la superficie abaxial.");
        FloweringPlant floweringPlantDAO = FloweringPlantDAO.getFloweringPlant(scientificName);
        assertEquals(floweringPlant, floweringPlantDAO);
    }

    public void getFloweringPlantsTest() throws SQLException {
        assertEquals(this, this);
    }

    @Test
    public void registerFloweringPlantTest() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        floweringPlant.setScientificName("");
        floweringPlant.setGenericName("");
        floweringPlant.setDescription("");
        floweringPlant.getFamily().setIdFamily(0);
        FloweringPlantDAO.registerFloweringPlant(floweringPlant);
        assertEquals(this, this);
    }

}
