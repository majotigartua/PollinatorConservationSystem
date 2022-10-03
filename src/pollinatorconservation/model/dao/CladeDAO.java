package pollinatorconservation.model.dao;

import educationaloffermanagement.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pollinatorconservation.model.pojo.Clade;

public class CladeDAO {

    public static ArrayList<Clade> getClades() throws SQLException {
        ArrayList<Clade> clades = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.open();
        String query = "SELECT * FROM clade ORDER BY name ASC";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configureQuery = database.prepareStatement(query);
            ResultSet result = configureQuery.executeQuery();
            while (result.next()) {
                Clade clade = new Clade();
                clade.setIdClade(result.getInt("idClade"));
                clade.setName(result.getString("name"));
                clades.add(clade);
            }
        } catch (SQLException exception) {
            System.err.println("No connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return clades;
    }

}