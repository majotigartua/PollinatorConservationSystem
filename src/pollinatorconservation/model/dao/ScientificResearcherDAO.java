package pollinatorconservation.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.DatabaseConnection;
import pollinatorconservation.model.pojo.Role;
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
    public static int editScientificResearcher(ScientificResearcher scientificResearcher) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE user SET username = ?, name = ?, paternalSurname = ?, maternalSurname = ?, password = ?, professionalLicenseNumber = ?, idRole = ?\n"
                + "WHERE username = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, scientificResearcher.getUsername());
            configureSentence.setString(2, scientificResearcher.getName());
            configureSentence.setString(3, scientificResearcher.getPaternalSurname());
            configureSentence.setString(4, scientificResearcher.getMaternalSurname());
            configureSentence.setString(5, scientificResearcher.getPassword());
            configureSentence.setString(6, scientificResearcher.getProfessionalLicenseNumber());
            configureSentence.setInt(7, scientificResearcher.getRole().getIdRole());
            configureSentence.setString(8, scientificResearcher.getUsername());
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
     public static ScientificResearcher getScientificResearcher(String username) throws SQLException {
        ScientificResearcher scientificResearcher = new ScientificResearcher();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT * from user WHERE username = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, username);
            ResultSet result = configureQuery.executeQuery();
            if (result.next()) {
                scientificResearcher.setUsername(result.getString("username"));
                scientificResearcher.setName(result.getString("name"));
                scientificResearcher.setPaternalSurname(result.getString("paternalSurname"));
                scientificResearcher.setMaternalSurname(result.getString("maternalSurname"));
                scientificResearcher.setProfessionalLicenseNumber(result.getString("professionalLicenseNumber"));
                Role role = new Role();
                role.setIdRole(result.getInt("idRole"));
                scientificResearcher.setRole(role);
            }
        } catch (SQLException ex) {
            scientificResearcher = null;
        } finally {
            databaseConnection.close();
        }
        return scientificResearcher;
    }
    public static ArrayList<ScientificResearcher> getSientificsResearcher() throws SQLException {
        ArrayList<ScientificResearcher> scientificsResearcher = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT user.name, user.paternalSurname, user.maternalSurname, user.username  FROM user";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                ScientificResearcher scientificResearcher = new ScientificResearcher();
                scientificResearcher.setUsername(result.getString("username"));
                scientificResearcher.setName(result.getString("name"));
                scientificResearcher.setPaternalSurname(result.getString("paternalSurname"));
                scientificResearcher.setMaternalSurname(result.getString("maternalSurname"));
                scientificsResearcher.add(scientificResearcher);
            }
        } catch (SQLException exception) {
            scientificsResearcher = null;
        } finally {
            databaseConnection.close();
        }
        return scientificsResearcher;
    }
    public static int deleteScientificResearcher(String name) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "DELETE from user WHERE name = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, name);
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
}