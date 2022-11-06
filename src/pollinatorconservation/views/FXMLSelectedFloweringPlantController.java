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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pollinatorconservation.model.dao.FloweringPlantDAO;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLSelectedFloweringPlantController implements Initializable {

    @FXML
    private TableView<FloweringPlant> floweringPlantTable;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    
    private int typeOfViewToConfigure;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        FloweringPlant floweringPlant = floweringPlantTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditFloweringPlant.fxml"));
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.EDIT_WINDOW_CODE, floweringPlant);
            Stage stage = new Stage();
            Scene editFloweringPlantView = new Scene(root);
            stage.setScene(editFloweringPlantView);
            stage.setTitle("Editar planta florífera");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"\" window...");
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }
    
    @FXML
    private void deleteButtonClick(ActionEvent event) throws SQLException, IOException {
        String scientificName = floweringPlantTable.getSelectionModel().getSelectedItem().getScientificName();
        int responseCode = FloweringPlantDAO.deleteFloweringPlant(scientificName);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se eliminó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            closePopUpWindow();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }
    
    private void loadFloweringPlants() throws SQLException {
        ArrayList<FloweringPlant> floweringPlant = new ArrayList<FloweringPlant>();
        floweringPlant = (ArrayList<FloweringPlant>) FloweringPlantDAO.getFloweringPlants();
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
}