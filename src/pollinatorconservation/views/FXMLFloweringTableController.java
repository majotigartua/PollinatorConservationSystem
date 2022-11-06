/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pollinatorconservation.PollinatorConservation;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author alvaro
 */
public class FXMLFloweringTableController implements Initializable {

    @FXML
    private TableView<FloweringPlant> floweringTableView;
    @FXML
    private TableColumn genericNameTableColumn;
    @FXML
    private TableColumn scientificNameTableColumn;
    @FXML
    private TableColumn cladeTableColumn;
    @FXML
    private Button checkButton;
    
    private ObservableList<FloweringPlant> floweringsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkButton.disableProperty().bind(Bindings.isEmpty(floweringTableView.getSelectionModel().getSelectedItems()));
        configureFloweringTable();
        try{
            setFloweringTable();
        }catch (SQLException ex){
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
            goToMainMenu();
        }
    }    

    @FXML
    private void checkButtonClick(ActionEvent event) {
        
        goToCheckFlowering();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        
        goToMainMenu();
    }

    private void configureFloweringTable() {
        scientificNameTableColumn.setCellValueFactory(new PropertyValueFactory("scientificName"));
        genericNameTableColumn.setCellValueFactory(new PropertyValueFactory("genericName"));
        cladeTableColumn.setCellValueFactory(new PropertyValueFactory("clade"));
        floweringsList = FXCollections.observableArrayList();
    }

    private void setFloweringTable() throws SQLException{
        ArrayList<FloweringPlant> floweringPlant = new ArrayList<FloweringPlant>();
        if(!floweringPlant.isEmpty()){
            floweringsList.addAll(floweringPlant);
            floweringTableView.setItems(floweringsList);
        }
    }

    private void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
            try {
            Parent root = loader.load();
            FXMLMainMenuController mainMenuController = loader.getController();
            mainMenuController.configureView(null);
            Stage stage = (Stage) floweringTableView.getScene().getWindow();
            Scene mainMenuView = new Scene(root);
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Main Menu\" window...");
        }
    }

    private void goToCheckFlowering() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFloweringPlant.fxml"));
        FloweringPlant floweringPlant = selectFloweringPlant();
        try {
            Parent root = loader.load();
            FXMLFloweringPlantController floweringPlantController = loader.getController();
            floweringPlantController.configureView(Constants.QUERY_WINDOW_CODE, floweringPlant);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consultar planta florifera");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Consult flowering plant.\" window...");
        }
    }

    private FloweringPlant selectFloweringPlant() {
        FloweringPlant floweringPlant = null;
        int row = floweringTableView.getSelectionModel().getSelectedIndex();
        if(row >= 0){
            floweringPlant = floweringsList.get(row);
        }
        return floweringPlant;
    }
    
}
