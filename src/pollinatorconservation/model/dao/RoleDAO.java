package pollinatorconservation.model.dao;

import pollinatorconservation.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pollinatorconservation.model.pojo.Role;

public class RoleDAO {

    public static Role getRoleByUser(String username) throws SQLException {
        Role role = new Role();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT role.* FROM role\n"
                + "INNER JOIN user\n"
                + "ON role.idRole = user.idRole\n"
                + "WHERE user.username = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, username);
            ResultSet result = configureQuery.executeQuery();
            if (result.next()) {
                role.setIdRole(result.getInt("idRole"));
                role.setName(result.getString("name"));
            }
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return role;
    }

}