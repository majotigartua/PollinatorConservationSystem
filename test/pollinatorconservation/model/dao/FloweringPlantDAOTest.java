package pollinatorconservation.model.dao;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;
import pollinatorconservation.model.pojo.FloweringPlant;


public class FloweringPlantDAOTest {

    /**
     * Test of registerFloweringPlant method, of class FloweringPlantDAO.
     */
    @Test
    public void succeededTestRegisterFloweringPlant() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        floweringPlant.setDescription("");
        floweringPlant.setGenericName("");
        floweringPlant.setScientificName("");
        floweringPlant.getFamily().setIdFamily(0);
        
        FloweringPlantDAO.registerFloweringPlant(floweringPlant);
        
        assertEquals(this, this); // The read method will help to complete the test.
    }
    
    @Test
    public void succeededTestEditFloweringPlant() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        String oldFloweringPlantName = "Amborella trichopoda";
        floweringPlant.setDescription("");
        floweringPlant.setGenericName("");
        floweringPlant.setScientificName("");
        floweringPlant.getFamily().setIdFamily(0);
        FloweringPlantDAO floweringPlantDao = new FloweringPlantDAO();
        floweringPlantDao.editFloweringPlant(floweringPlant);
        
        assertEquals(this, this); // The read method will help to complete the test.
    }
    
    public void succeededTestDeleteFloweringPlant() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        String oldFloweringPlantName = "Amborella trichopoda";
        FloweringPlantDAO floweringPlantDao = new FloweringPlantDAO();
        floweringPlantDao.deleteFloweringPlant(oldFloweringPlantName);
        
        assertEquals(this, this); // The read method will help to complete the test.
    }
    public void succeededTestGetFloweringPlant() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        String floweringPlantName = "";
        floweringPlant.setDescription("Es un arbusto de gran porte o arbolito, algo trepador, siempreverde, de hasta"
                + " 8 m. de altura, tomento de pelos uniseriados multicelulares (a veces unicelulares). Sus hojas son "
                + "alternas, espiraladas a dísticas en la madurez, "
                + "pecioladas, sin estípulas, de margen ondulado a dentado, a veces pinnatífidas, pinnatinervias, "
                + "las venas conexas cerca del margen, estomas paracíticos a anomocíticos, sólo en la superficie abaxial.");
        floweringPlant.setGenericName("Amborella");
        floweringPlant.setScientificName("Amborella trichopoda");
        FloweringPlantDAO floweringPlantDao = new FloweringPlantDAO();
        floweringPlant = floweringPlantDao.getFloweringPlant(floweringPlantName);
        
        assertEquals(this, this); // The read method will help to complete the test.
    }
}
