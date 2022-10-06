package pollinatorconservation.model.dao;

import pollinatorconservation.model.DatabaseConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class UserDAO {

    public static User login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        User user = new User();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT user.* FROM user\n"
                + "WHERE username = ? AND password = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            configureQuery.setString(1, username);
            configureQuery.setString(2, (Utilities.computeSHA256Hash(password)));
            ResultSet result = configureQuery.executeQuery();
            if (result.next()) {
                user.setName(result.getString("name"));
                user.setPaternalSurname(result.getString("paternalSurname"));
                user.setMaternalSurname(result.getString("maternalSurname"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setRole(RoleDAO.getRoleByUser(username));
                user.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            } else {
                user.setResponseCode(Constants.INVALID_ENTERED_DATA_CODE);
            }
        } catch (SQLException exception) {
            user.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return user;
    }
    
}