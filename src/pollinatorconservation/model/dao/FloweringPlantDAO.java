package pollinatorconservation.model.dao;

import pollinatorconservation.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;

public class FloweringPlantDAO {

    public static int registerFloweringPlant(FloweringPlant floweringPlant) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO floweringPlant (scientificName, genericName, description, idFamily)\n"
                + "VALUES (?, ?, ?, ?)";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, floweringPlant.getScientificName());
            configureSentence.setString(2, floweringPlant.getGenericName());
            configureSentence.setString(3, floweringPlant.getDescription());
            configureSentence.setInt(4, floweringPlant.getFamily().getIdFamily());
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.SPECIES_ALREADY_REGISTERED;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

}