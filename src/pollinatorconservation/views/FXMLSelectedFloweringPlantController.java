/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pollinatorconservation.views;

import java.io.File;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import pollinatorconservation.model.dao.FloweringPlantDAO;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLSelectedFloweringPlantController implements Initializable {

    @FXML
    private TableView<FloweringPlant> floweringPlantTable;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     */
    FloweringPlantDAO floweringPlantDao = new FloweringPlantDAO();
    private int typeOfViewToConfigure;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deleteButton.disableProperty().bind(Bindings.isEmpty(floweringPlantTable.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(Bindings.isEmpty(floweringPlantTable.getSelectionModel().getSelectedItems()));
        try {
            loadFloweringPlants();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }    
    
    

    @FXML
    private void editButtonClick(ActionEvent event) {
        String scientificName = floweringPlantTable.getSelectionModel().getSelectedItem().toString();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditFloweringPlant.fxml"));
        try {
            Parent root = loader.load();
            FXMLEditFloweringPlantController editFloweringPlantController = loader.getController();
            editFloweringPlantController.configureView(Constants.REGISTRATION_WINDOW_CODE, scientificName);
            Stage stage = new Stage();
            Scene editFloweringPlantView = new Scene(root);
            stage.setScene(editFloweringPlantView);
            stage.setTitle("Editar planta florífera");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Edit Flowering Plant\" window...");
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }


    @FXML
    private void deleteButtonClick(ActionEvent event) throws SQLException, IOException {
        String scientificName = floweringPlantTable.getSelectionModel().getSelectedItem().toString();
        deleteFloweringPlant(scientificName);
    }
    private void loadFloweringPlants() throws SQLException {
        ArrayList<FloweringPlant> floweringPlant = new ArrayList<FloweringPlant>();
        floweringPlant = (ArrayList<FloweringPlant>) floweringPlantDao.getFloweringPlants();
        ObservableList<FloweringPlant> floweringPlantsTable = FXCollections.observableArrayList(floweringPlant);
        TableColumn columnTitleScientificName = new TableColumn("Nombre científico");
        TableColumn columnTitleGenericName = new TableColumn("Nombre genérico");
        floweringPlantTable.getColumns().addAll(columnTitleScientificName, columnTitleGenericName);
        columnTitleScientificName.setCellValueFactory(new PropertyValueFactory<FloweringPlant,String>("scientificName"));
        columnTitleGenericName.setCellValueFactory(new PropertyValueFactory<FloweringPlant,String>("genericName"));
        floweringPlantTable.setItems(floweringPlantsTable);
    }
    private void closePopUpWindow() {
        Stage stage = (Stage) floweringPlantTable.getScene().getWindow();
        stage.close();
    }
    private void deleteFloweringPlant(String scientificName) throws SQLException, IOException {
        int responseCode = floweringPlantDao.deleteFloweringPlant(scientificName);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("Se eliminó correctamente el registro.\n",
                        Alert.AlertType.INFORMATION);
                closePopUpWindow();
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }
}
