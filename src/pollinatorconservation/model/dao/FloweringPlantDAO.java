package pollinatorconservation.model.dao;

import pollinatorconservation.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.model.pojo.Order;
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
    public int editFloweringPlant(FloweringPlant newfloweringPlant, String floweringPlantName) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE floweringPlant SET scientificName = ?, genericName = ?, description = ?, idFamily = ?)\n"
                + "WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, newfloweringPlant.getScientificName());
            configureSentence.setString(2, newfloweringPlant.getGenericName());
            configureSentence.setString(3, newfloweringPlant.getDescription());
            configureSentence.setInt(4, newfloweringPlant.getFamily().getIdFamily());
            configureSentence.setString(5, floweringPlantName);
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.SPECIES_ALREADY_REGISTERED;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    public int deleteFloweringPlant(String floweringPlantName) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "Delete from floweringPlant WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, floweringPlantName);
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
   
    public FloweringPlant getFloweringPlant(String floweringPlantName) throws SQLException {
        int responseCode;
        FloweringPlant floweringPlantObtained = new FloweringPlant();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT * from floweringPlant WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, floweringPlantName);
            ResultSet result = configureQuery.executeQuery();
            if (result.next() == true){
                floweringPlantObtained.setScientificName(result.getString("scientificName"));
                floweringPlantObtained.setGenericName(result.getString("genericName"));
                floweringPlantObtained.setDescription(result.getString("description"));
            }
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return floweringPlantObtained;
    }
    public ArrayList<FloweringPlant> getFloweringPlants() throws SQLException {
        ArrayList<FloweringPlant> floweringPlants = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT * FROM floweringPlant";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                FloweringPlant floweringPlantObtained = new FloweringPlant();
                floweringPlantObtained.setScientificName(result.getString("scientificName"));
                floweringPlantObtained.setGenericName(result.getString("genericName"));
                floweringPlantObtained.setDescription(result.getString("description"));
            }
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return floweringPlants;
    }
}