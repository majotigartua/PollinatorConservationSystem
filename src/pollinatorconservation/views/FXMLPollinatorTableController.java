/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import pollinatorconservation.model.dao.PollinatorDAO;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class FXMLPollinatorTableController implements Initializable {

    @FXML
    private Button checkButton;
    @FXML
    private TableView<Pollinator> pollinatorTableView;
    @FXML
    private TableColumn genericNameTableColumn;
    @FXML
    private TableColumn scientificNameTableColumn;
    @FXML
    private TableColumn orderTableColumn;

    private ObservableList<Pollinator> pollinatorsList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkButton.disableProperty().bind(Bindings.isEmpty(pollinatorTableView.getSelectionModel().getSelectedItems()));
        configurePollinatorTable();
        try {
            setPollinatorTable();
        } catch (SQLException ex) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
            goToMainMenu();
        }
    }    

    @FXML
    private void checkButtonClick(ActionEvent event) {
        goToCheckPollinator();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        goToMainMenu();
    }
    
    private void configurePollinatorTable() {
        scientificNameTableColumn.setCellValueFactory(new PropertyValueFactory("scientificName"));
        genericNameTableColumn.setCellValueFactory(new PropertyValueFactory("genericName"));
        orderTableColumn.setCellValueFactory(new PropertyValueFactory("family"));
        pollinatorsList = FXCollections.observableArrayList();
        
    }
    
    private void setPollinatorTable() throws SQLException {
        ArrayList<Pollinator> pollinators = PollinatorDAO.checkPollinators();
        if(!pollinators.isEmpty()) {
            pollinatorsList.addAll(pollinators);
            pollinatorTableView.setItems(pollinatorsList);
        }
    }
    
    public void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
        try {
            Parent root = loader.load();
            FXMLMainMenuController mainMenuController = loader.getController();
            mainMenuController.configureView(null);
            Stage stage = (Stage) pollinatorTableView.getScene().getWindow();
            Scene mainMenuView = new Scene(root);
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Main Menu\" window...");
        }
    }
    
    public void goToCheckPollinator() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinator.fxml"));
        Pollinator pollinator = selectPollinator();
        try {
            Parent root = loader.load();
            FXMLPollinatorController pollinatorController = loader.getController();
            pollinatorController.configureView(Constants.QUERY_WINDOW_CODE, pollinator);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consultar especie polinizadora");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Register flowering plant.\" window...");
        }
    }
    
    public Pollinator selectPollinator() {
        Pollinator pollinator = null;
        int row = pollinatorTableView.getSelectionModel().getSelectedIndex();
        if(row >= 0) {
            pollinator = pollinatorsList.get(row);
        }
        return pollinator;
    }
    
}
