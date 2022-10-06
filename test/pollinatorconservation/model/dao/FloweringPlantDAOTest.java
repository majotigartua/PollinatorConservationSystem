package pollinatorconservation.model.dao;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pollinatorconservation.model.pojo.FloweringPlant;

public class FloweringPlantDAOTest {
    
    @Test
    public void registerFloweringPlantSuccesfulTest() throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        floweringPlant.setScientificName("");
        floweringPlant.setGenericName("");
        floweringPlant.setDescription("");
        floweringPlant.getFamily().setIdFamily(0);
        
        int testResult= FloweringPlantDAO.registerFloweringPlant(floweringPlant);
        
        assertEquals(testResult, pollinatorconservation.util.Constants.CORRECT_OPERATION_CODE);
    }
}