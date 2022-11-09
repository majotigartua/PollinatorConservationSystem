package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pollinatorconservation.PollinatorConservation;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;

public class FXMLMainMenuController implements Initializable {

    @FXML
    private Button registerPollinatorButton;
    @FXML
    private Button editPollinatorButton;
    @FXML
    private Button consultPollinatorButton;
    @FXML
    private Button registerFloweringPlantButton;
    @FXML
    private Button editFloweringPlantButton;
    @FXML
    private Button consultFloweringPlantButton;
    @FXML
    private Button registerScientificResearcherButton;
    @FXML
    private Button editScientificResearcherButton;

    private static User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(User user) {
        this.user = user;
        if (user != null) {
            int idRole = user.getRole().getIdRole();
            if (idRole == Constants.ID_SCIENTIFIC_RESEARCHER_ROLE) {
                registerPollinatorButton.setVisible(true);
                editPollinatorButton.setVisible(true);
                registerFloweringPlantButton.setVisible(true);
                editFloweringPlantButton.setVisible(true);
            } else {
                registerScientificResearcherButton.setVisible(true);
                editScientificResearcherButton.setVisible(true);
            }
        } else {
            consultPollinatorButton.setVisible(true);
            consultFloweringPlantButton.setVisible(true);
        }
    }

    @FXML
    private void registerPollinatorButtonClick(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinator.fxml"));
        try {
            Parent root = loader.load();
            FXMLPollinatorController pollinatorController = loader.getController();
            pollinatorController.configureView(Constants.REGISTRATION_WINDOW_CODE, null, null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar especie polinizadora.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register pollinator.\" window...");
        }
    }

    @FXML
    private void editPollinatorButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinators.fxml"));
        try {
            Parent root = loader.load();
            FXMLPollinatorsController pollinatorsController = loader.getController();
            pollinatorsController.configureView(user, Constants.EDIT_WINDOW_CODE);
            Stage stage = (Stage) registerPollinatorButton.getScene().getWindow();
            Scene pollinatorsView = new Scene(root);
            stage.setScene(pollinatorsView);
            stage.setTitle("Especies polinizadoras.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Pollinators\" window...");
        }
    }

    @FXML
    private void consultPollinatorButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinators.fxml"));
        try {
            Parent root = loader.load();
            FXMLPollinatorsController pollinatorsController = loader.getController();
            pollinatorsController.configureView(user, Constants.QUERY_WINDOW_CODE);
            Stage stage = (Stage) registerPollinatorButton.getScene().getWindow();
            Scene pollinatorsView = new Scene(root);
            stage.setScene(pollinatorsView);
            stage.setTitle("Especies polinizadoras.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Pollinators\" window...");
        }
    }

    @FXML
    private void registerFloweringPlantButtonClick(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlant.fxml"));
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.REGISTRATION_WINDOW_CODE, null, null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar planta florífera.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register flowering plant.\" window...");
        }
    }

    @FXML
    private void editFloweringPlantButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlants.fxml"));
        try {
            Parent root = loader.load();
            FXMLFloweringPlantsController floweringPlantsController = loader.getController();
            floweringPlantsController.configureView(user, Constants.EDIT_WINDOW_CODE);
            Stage stage = (Stage) registerPollinatorButton.getScene().getWindow();
            Scene floweringPlantsView = new Scene(root);
            stage.setScene(floweringPlantsView);
            stage.setTitle("Plantas floríferas.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Flowering plants.\" window...");
        }
    }

    @FXML
    private void consultFloweringPlantButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlants.fxml"));
        try {
            Parent root = loader.load();
            FXMLFloweringPlantsController floweringPlantsController = loader.getController();
            floweringPlantsController.configureView(user, Constants.QUERY_WINDOW_CODE);
            Stage stage = (Stage) registerPollinatorButton.getScene().getWindow();
            Scene floweringPlantsView = new Scene(root);
            stage.setScene(floweringPlantsView);
            stage.setTitle("Plantas floríferas.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Flowering plants\" window...");
        }
    }

    @FXML
    private void registerScientificResearcherButtonClick(ActionEvent event) {
    }

    @FXML
    private void editScientificResearcherButtonClick(ActionEvent event) {
    }

    @FXML
    private void signOutButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
        try {
            Parent root = loader.load();
            FXMLLoginController loginController = loader.getController();
            Stage stage = (Stage) registerPollinatorButton.getScene().getWindow();
            Scene loginView = new Scene(root);
            stage.setScene(loginView);
            stage.setTitle("Iniciar sesión.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Login\" window...");
        }
    }

}
