package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pollinatorconservation.PollinatorConservation;
import pollinatorconservation.interfaces.IFloweringPlant;
import pollinatorconservation.model.dao.FloweringPlantDAO;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLFloweringPlantsController implements Initializable, IFloweringPlant {

    @FXML
    private Label instructionLabel;
    @FXML
    private TableView<FloweringPlant> floweringPlantsTableView;
    @FXML
    private TableColumn genericNameTableColumn;
    @FXML
    private TableColumn scientificNameTableColumn;
    @FXML
    private Button consultButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteFloweringPlantButton;

    private User user;
    private ObservableList<FloweringPlant> floweringPlants;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editButton.disableProperty().bind(Bindings.isEmpty(floweringPlantsTableView.getSelectionModel().getSelectedItems()));
        deleteFloweringPlantButton.disableProperty().bind(Bindings.isEmpty(floweringPlantsTableView.getSelectionModel().getSelectedItems()));
        consultButton.disableProperty().bind(Bindings.isEmpty(floweringPlantsTableView.getSelectionModel().getSelectedItems()));
        configureFloweringPlantsTableView();
        try {
            loadFloweringPlants();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.",
                    Alert.AlertType.ERROR);
            goToMainMenu();
        }
    }

    private void configureFloweringPlantsTableView() {
        floweringPlants = FXCollections.observableArrayList();
        scientificNameTableColumn.setCellValueFactory(new PropertyValueFactory("scientificName"));
        genericNameTableColumn.setCellValueFactory(new PropertyValueFactory("genericName"));
    }

    private void loadFloweringPlants() throws SQLException {
        floweringPlantsTableView.getItems().clear();
        ArrayList<FloweringPlant> floweringPlantsQuery = FloweringPlantDAO.getFloweringPlants();
        if (!floweringPlantsQuery.isEmpty()) {
            floweringPlants.clear();
            floweringPlants.addAll(floweringPlantsQuery);
            floweringPlantsTableView.setItems(floweringPlants);
        }
    }

    private void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
        try {
            Parent root = loader.load();
            FXMLMainMenuController mainMenuController = loader.getController();
            mainMenuController.configureView(user);
            Stage stage = (Stage) floweringPlantsTableView.getScene().getWindow();
            Scene mainMenuView = new Scene(root);
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Main Menu\" window...");
        }
    }

    public void configureView(User user, int typeOfViewToConfigure) {
        this.user = user;
        if (typeOfViewToConfigure == Constants.EDIT_WINDOW_CODE) {
            editButton.setVisible(true);
            deleteFloweringPlantButton.setVisible(true);
        } else {
            instructionLabel.setText("Seleccione una planta florífera y dé clic en el botón de \"Consultar\"");
            consultButton.setVisible(true);
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        goToMainMenu();
    }

    @FXML
    private void editButtonClick(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlant.fxml"));
        String scientificName = floweringPlantsTableView.getSelectionModel().getSelectedItem().getScientificName();
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.EDIT_WINDOW_CODE, scientificName, this);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar planta florifera.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Edit flowering plant.\" window...");
        }
    }

    @FXML
    private void deleteFloweringPlantButtonClick(ActionEvent event) throws SQLException {
        String scientificName = floweringPlantsTableView.getSelectionModel().getSelectedItem().getScientificName();
        int responseCode = FloweringPlantDAO.deleteFloweringPlant(scientificName);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se eliminó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            loadFloweringPlants();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void consultButtonClick(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlant.fxml"));
        String scientificName = floweringPlantsTableView.getSelectionModel().getSelectedItem().getScientificName();
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.QUERY_WINDOW_CODE, scientificName, null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consultar planta florifera.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Consult flowering plant.\" window...");
        }
    }

    @Override
    public void updateFloweringPlants() {
        try {
            loadFloweringPlants();
        } catch (SQLException ex) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.",
                    Alert.AlertType.ERROR);
        }
    }

}