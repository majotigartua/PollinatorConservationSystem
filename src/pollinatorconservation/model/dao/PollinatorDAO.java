package pollinatorconservation.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.DatabaseConnection;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.Order;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.util.Constants;

public class PollinatorDAO {

    public static int deletePollinator(String pollinatorName) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "DELETE from pollinator WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, pollinatorName);
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int editPollinator(Pollinator pollinator) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE pollinator SET genericName = ?, description = ?, idFamily = ?\n"
                + "WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, pollinator.getGenericName());
            configureSentence.setString(2, pollinator.getDescription());
            configureSentence.setInt(3, pollinator.getFamily().getIdFamily());
            configureSentence.setString(4, pollinator.getScientificName());
            int affectedRows = configureSentence.executeUpdate();
            responseCode = (affectedRows == 1) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException ex) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static Pollinator getPollinator(String scientificName) throws SQLException {
        Pollinator pollinator = new Pollinator();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT pollinator.*, family.name as family, pollinatorconservation.order.* FROM pollinator\n"
                + "INNER JOIN family \n"
                + "ON pollinator.idFamily = family.idFamily\n"
                + "INNER JOIN pollinatorconservation.order\n"
                + "ON family.idOrder = pollinatorconservation.order.idOrder\n"
                + "WHERE pollinator.scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, scientificName);
            ResultSet result = configureQuery.executeQuery();
            if (result.next() == true) {
                pollinator.setScientificName(result.getString("scientificName"));
                pollinator.setGenericName(result.getString("genericName"));
                pollinator.setDescription(result.getString("description"));
                Order order = new Order();
                order.setIdOrder((result.getInt("idOrder")));
                order.setName((result.getString("name")));
                Family family = new Family();
                family.setIdFamily(result.getInt("idFamily"));
                family.setName(result.getString(("family")));
                family.setOrder(order);
                pollinator.setFamily(family);
            }
        } catch (SQLException ex) {
            pollinator = null;
        } finally {
            databaseConnection.close();
        }
        return pollinator;
    }

    public static ArrayList<Pollinator> getPollinators() throws SQLException {
        ArrayList<Pollinator> pollinators = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT * FROM pollinator";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                Pollinator pollinator = new Pollinator();
                pollinator.setScientificName(result.getString("scientificName"));
                pollinator.setGenericName(result.getString("genericName"));
                pollinators.add(pollinator);
            }
        } catch (SQLException exception) {
            pollinators = null;
        } finally {
            databaseConnection.close();
        }
        return pollinators;
    }

    public static int registerPollinator(Pollinator pollinator) throws SQLException {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO pollinator (scientificName, genericName, description, idFamily)\n"
                + "VALUES (?, ?, ?, ?)";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, pollinator.getScientificName());
            configureSentence.setString(2, pollinator.getGenericName());
            configureSentence.setString(3, pollinator.getDescription());
            configureSentence.setInt(4, pollinator.getFamily().getIdFamily());
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