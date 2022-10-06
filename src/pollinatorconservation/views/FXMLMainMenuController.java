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
import javafx.stage.Modality;
import javafx.stage.Stage;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;

public class FXMLMainMenuController implements Initializable {

    private static User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(User user) {
        this.user = user;
    }

    @FXML
    private void registerFloweringPlantButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlant.fxml"));
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.REGISTRATION_WINDOW_CODE, null);
            Stage stage = new Stage();
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
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register flowering plant.\" window...");
        }
    }
    
}