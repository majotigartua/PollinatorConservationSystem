/*

 * Author:
 * Date:
 * Description:
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    public static ArrayList<Pollinator> checkPollinators() throws SQLException {
        ArrayList<Pollinator> pollinators = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT pollinator.*, family.name as family, family.idOrder, pollinatorconservation.order.name as 'order' FROM pollinator\n"
                + "INNER JOIN family ON pollinator.idFamily = family.idFamily\n"
                + "INNER JOIN pollinatorconservation.order ON family.idOrder = pollinatorconservation.order.idOrder";
        try (Connection database = databaseConnection.open()){
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while(result.next()) {
                Pollinator pollinator = new Pollinator();
                pollinator.setScientificName(result.getString("scientificName"));
                pollinator.setGenericName(result.getString("genericName"));
                pollinator.setDescription(result.getString("description"));
                
                Family family = new Family();
                family.setIdFamily(result.getInt("idFamily"));
                family.setName(result.getString("family"));
                
                Order order = new Order();
                order.setIdOrder(result.getInt("idOrder"));
                order.setName(result.getString("order"));
                
                family.setOrder(order);
                pollinator.setFamily(family);
                
                pollinators.add(pollinator);
            }
            
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return pollinators;
    }

    public int deletePollinator(String pollinatorName) throws SQLException{
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "Delete from pollinator WHERE scientificName = ?";
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
    
    public ArrayList<Pollinator> getPollinators() throws SQLException {
        ArrayList<Pollinator> pollinators = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT * FROM pollinator";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                Pollinator pollinatorObtained = new Pollinator();
                pollinatorObtained.setScientificName(result.getString("scientificName"));
                pollinatorObtained.setGenericName(result.getString("genericName"));
                pollinatorObtained.setDescription(result.getString("description"));
            }
        } catch (SQLException exception) {
            System.err.println("No hay conexion con la base de datos. Intentelo más tarde.");
        } finally {
            databaseConnection.close();
        }
        return pollinators;
    }

    public Pollinator getPollinator(String pollinatorName) throws SQLException{
        int responseCode;
        Pollinator pollinatorObtained = new Pollinator();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT * from pollinator WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, pollinatorName);
            ResultSet result = configureQuery.executeQuery();
            if (result.next() == true){
                pollinatorObtained.setScientificName(result.getString("scientificName"));
                pollinatorObtained.setGenericName(result.getString("genericName"));
                pollinatorObtained.setDescription(result.getString("description"));
            }
        } catch (SQLException ex) {
            responseCode = Constants.CORRECT_OPERATION_CODE;
        } finally {
            databaseConnection.close();
        }
        return pollinatorObtained;
    }

    public int editPollinator(Pollinator newPollinator, String pollinatorName) throws SQLException{
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE pollinator SET scientificName = ?, genericName = ?, description = ?, idFamily = ?)\n"
                + "WHERE scientificName = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureSentence = database.prepareStatement(sentence);
            configureSentence.setString(1, newPollinator.getScientificName());
            configureSentence.setString(2, newPollinator.getGenericName());
            configureSentence.setString(3, newPollinator.getDescription());
            configureSentence.setInt(4, newPollinator.getFamily().getIdFamily());
            configureSentence.setString(5, pollinatorName);
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
