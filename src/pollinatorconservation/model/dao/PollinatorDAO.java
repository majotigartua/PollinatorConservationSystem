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
import java.sql.SQLException;
import pollinatorconservation.model.DatabaseConnection;
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
}
