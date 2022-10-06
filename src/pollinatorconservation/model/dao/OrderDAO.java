package pollinatorconservation.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.DatabaseConnection;
import pollinatorconservation.model.pojo.Order;

public class OrderDAO {

    public static ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT * FROM pollinatorConservation.order ORDER BY name ASC";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getInt("idOrder"));
                order.setName(result.getString("name"));
                orders.add(order);
            }
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return orders;
    }

}