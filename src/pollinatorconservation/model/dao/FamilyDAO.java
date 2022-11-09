package pollinatorconservation.model.dao;

import pollinatorconservation.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.pojo.Clade;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.Order;

public class FamilyDAO {

    public static ArrayList<Family> getFamiliesByClade(Clade clade) throws SQLException {
        ArrayList<Family> families = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT * FROM family\n"
                + "WHERE idClade = ?\n"
                + "ORDER BY name ASC";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setInt(1, clade.getIdClade());
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                Family family = new Family();
                family.setIdFamily(result.getInt("idFamily"));
                family.setName(result.getString("name"));
                family.setClade(clade);
                families.add(family);
            }
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return families;
    }

    public static ArrayList<Family> getFamiliesByOrder(Order order) throws SQLException {
        ArrayList<Family> families = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT * FROM family\n"
                + "WHERE idOrder = ?\n"
                + "ORDER BY name ASC";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setInt(1, order.getIdOrder());
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                Family family = new Family();
                family.setIdFamily(result.getInt("idFamily"));
                family.setName(result.getString("name"));
                family.setOrder(order);
                families.add(family);
            }
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return families;
    }

}