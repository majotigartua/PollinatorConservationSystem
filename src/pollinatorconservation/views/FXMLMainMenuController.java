package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
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

    private static User user;
    @FXML
    private Button registerPollinatorButton;
    @FXML
    private Button editPollinatorButton;
    @FXML
    private Button editFloweringPlantButton;
    @FXML
    private Button registerFloweringPlantButton;
    @FXML
    private Button checkPollinatorButton;
    @FXML
    private Button checkFloweringPlantButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(User user) {
        this.user = user;
        showGuestButtons();
    }
    
    public void showGuestButtons() {
        if(user == null) {
            registerPollinatorButton.setVisible(false);
            registerFloweringPlantButton.setVisible(false);
            editPollinatorButton.setVisible(false);
            editFloweringPlantButton.setVisible(false);
            checkPollinatorButton.setVisible(true);
            checkFloweringPlantButton.setVisible(true);
        }
    }

    @FXML
    private void registerFloweringPlantButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlant.fxml"));
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.REGISTRATION_WINDOW_CODE, null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register flowering plant.\" window...");
        }
    }

    @FXML
    private void registerPollinatorButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinator.fxml"));
        try {
            Parent root = loader.load();
            FXMLPollinatorController pollinatorController = loader.getController();
            pollinatorController.configureView(Constants.REGISTRATION_WINDOW_CODE, null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register flowering plant.\" window...");
        }
    }

    @FXML
    private void editFloweringPlantButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSelectedFloweringPlant.fxml"));
        try {
            Parent root = loader.load();
            FXMLSelectedFloweringPlantController selectedloweringPlantController = loader.getController();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            Scene selectedFloweringPlantView = new Scene(root);
            stage.setScene(selectedFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Selected flowering plant.\" window...");
        }
    }

    @FXML
    private void editPollinatorButtonClick(ActionEvent event) {
    }

    @FXML
    private void checkPollinatorButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinatorTable.fxml"));
        try {
            Parent root = loader.load();
            FXMLPollinatorTableController pollinatorTableController = loader.getController();
            Stage stage = (Stage) checkPollinatorButton.getScene().getWindow();
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.setTitle("Especies polinizadoras");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register flowering plant.\" window...");
        }
    }

    @FXML
    private void checkFloweringPlantButtonClick(ActionEvent event) {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringTable.fxml"));
        
    }
    
}