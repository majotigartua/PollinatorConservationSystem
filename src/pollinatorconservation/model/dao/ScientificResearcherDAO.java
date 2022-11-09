package pollinatorconservation.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pollinatorconservation.model.DatabaseConnection;
import pollinatorconservation.model.pojo.ScientificResearcher;
import pollinatorconservation.util.Constants;

public class ScientificResearcherDAO {

    public static int registerScientificResearcher(ScientificResearcher scientificResearcher) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO user (username, name, paternalSurname, maternalSurname, password, professionalLicenseNumber, idRole)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, scientificResearcher.getUsername());
            configureSentence.setString(2, scientificResearcher.getName());
            configureSentence.setString(3, scientificResearcher.getPaternalSurname());
            configureSentence.setString(4, scientificResearcher.getMaternalSurname());
            configureSentence.setString(5, scientificResearcher.getPassword());
            configureSentence.setString(6, scientificResearcher.getProfessionalLicenseNumber());
            configureSentence.setInt(7, scientificResearcher.getRole().getIdRole());
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.SCIENTIFIC_RESEARCHER_ALREADY_REGISTERED;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
}