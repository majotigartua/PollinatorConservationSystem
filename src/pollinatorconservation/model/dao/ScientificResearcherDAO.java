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

    public static int deleteScientificResearcher(String username) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "DELETE from user WHERE username = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, username);
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int editScientificResearcher(ScientificResearcher scientificResearcher) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE user SET name = ?, paternalSurname = ?, maternalSurname = ?, password = ?, professionalLicenseNumber = ?\n"
                + "WHERE username = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, scientificResearcher.getName());
            configureSentence.setString(2, scientificResearcher.getPaternalSurname());
            configureSentence.setString(3, scientificResearcher.getMaternalSurname());
            configureSentence.setString(4, scientificResearcher.getPassword());
            configureSentence.setString(5, scientificResearcher.getProfessionalLicenseNumber());
            configureSentence.setString(6, scientificResearcher.getUsername());
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

    public static ArrayList<ScientificResearcher> getScientificResearchers() throws SQLException {
        ArrayList<ScientificResearcher> scientificResearchers = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT name, paternalSurname, maternalSurname, username\n"
                + "FROM user\n"
                + "WHERE idRole = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setInt(1, Constants.ID_SCIENTIFIC_RESEARCHER_ROLE);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                ScientificResearcher scientificResearcher = new ScientificResearcher();
                scientificResearcher.setUsername(result.getString("username"));
                scientificResearcher.setName(result.getString("name"));
                scientificResearcher.setPaternalSurname(result.getString("paternalSurname"));
                scientificResearcher.setMaternalSurname(result.getString("maternalSurname"));
                scientificResearchers.add(scientificResearcher);
            }
        } catch (SQLException exception) {
            scientificResearchers = null;
        } finally {
            databaseConnection.close();
        }
        return scientificResearchers;
    }

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