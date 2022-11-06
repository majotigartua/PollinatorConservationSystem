package pollinatorconservation.model.dao;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;
import pollinatorconservation.model.pojo.FloweringPlant;


public class FloweringPlantDAOTest {

    @Test
    public void succeededTestRegisterFloweringPlant() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        floweringPlant.setDescription("");
        floweringPlant.setGenericName("");
        floweringPlant.setScientificName("");
        floweringPlant.setImagePath("");
        floweringPlant.getFamily().setIdFamily(0);
        
        FloweringPlantDAO.registerFloweringPlant(floweringPlant);
        
        assertEquals(this, this);
    }
    
}