package pollinatorconservation.model.dao;

import pollinatorconservation.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.pojo.Clade;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;

public class FloweringPlantDAO {

    public static int deleteFloweringPlant(String scientificName) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "DELETE from floweringPlant WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, scientificName);
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
        public static int editFloweringPlant(FloweringPlant floweringPlant) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE floweringPlant SET genericName = ?, description = ?, idFamily = ?\n"
                + "WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, floweringPlant.getGenericName());
            configureSentence.setString(2, floweringPlant.getDescription());
            configureSentence.setInt(3, floweringPlant.getFamily().getIdFamily());
            configureSentence.setString(4, floweringPlant.getScientificName());
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static FloweringPlant getFloweringPlant(String scientificName) throws SQLException {
        FloweringPlant floweringPlant = new FloweringPlant();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT floweringPlant.*, family.name as family, clade.idClade, clade.name AS clade FROM floweringPlant\n"
                + "INNER JOIN family\n"
                + "ON floweringPlant.idFamily = family.idFamily\n"
                + "INNER JOIN clade\n"
                + "ON family.idClade = clade.idClade\n"
                + "WHERE floweringPlant.scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, scientificName);
            ResultSet result = configureQuery.executeQuery();
            if (result.next()) {
                floweringPlant.setScientificName(result.getString("scientificName"));
                floweringPlant.setGenericName(result.getString("genericName"));
                floweringPlant.setDescription(result.getString("description"));           
                Clade clade = new Clade();
                clade.setIdClade((result.getInt("idClade")));
                clade.setName((result.getString("clade")));
                Family family = new Family();
                family.setIdFamily(result.getInt("idFamily"));
                family.setName(result.getString(("family")));               
                family.setClade(clade);
                floweringPlant.setFamily(family);
            }
        } catch (SQLException ex) {
            floweringPlant = null;
        } finally {
            databaseConnection.close();
        }
        return floweringPlant;
    }

    public static ArrayList<FloweringPlant> getFloweringPlants() throws SQLException {
        ArrayList<FloweringPlant> floweringPlants = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT floweringPlant.scientificName, floweringPlant.genericName FROM floweringPlant";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                FloweringPlant floweringPlant = new FloweringPlant();
                floweringPlant.setScientificName(result.getString("scientificName"));
                floweringPlant.setGenericName(result.getString("genericName"));
                floweringPlants.add(floweringPlant);
            }
        } catch (SQLException exception) {
            floweringPlants = null;
        } finally {
            databaseConnection.close();
        }
        return floweringPlants;
    }

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
