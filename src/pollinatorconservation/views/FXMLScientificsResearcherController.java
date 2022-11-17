/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.util.ArrayList;
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
import pollinatorconservation.interfaces.IScientificResearcher;
import pollinatorconservation.PollinatorConservation;
import pollinatorconservation.model.dao.ScientificResearcherDAO;
import pollinatorconservation.model.pojo.ScientificResearcher;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLScientificsResearcherController implements Initializable {

    @FXML
    private Button editButton;
    @FXML
    private Button deleteScientificButton;
    @FXML
    private Label instructionLabel;
    @FXML
    private TableView<ScientificResearcher> scientificsTableView;
    private TableColumn completeNameTableColumn;
    
    private User user;
    private ObservableList<ScientificResearcher> scientificsResearcher;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn paternalSurnameTableColum;
    @FXML
    private TableColumn maternalSurnameTableColum;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editButton.disableProperty().bind(Bindings.isEmpty(scientificsTableView.getSelectionModel().getSelectedItems()));
        deleteScientificButton.disableProperty().bind(Bindings.isEmpty(scientificsTableView.getSelectionModel().getSelectedItems()));
        configureScientificsTableView();
        try {
            loadScientificsResearcher();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.",
                    Alert.AlertType.ERROR);
            goToMainMenu();
        }
    } 
    
    private void configureScientificsTableView() {
        scientificsResearcher = FXCollections.observableArrayList();
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        paternalSurnameTableColum.setCellValueFactory(new PropertyValueFactory("paternalSurname"));
        maternalSurnameTableColum.setCellValueFactory(new PropertyValueFactory("maternalSurname"));
    }
    
    private void loadScientificsResearcher() throws SQLException {
        scientificsTableView.getItems().clear();
        ArrayList<ScientificResearcher> scientificResearcherQuery = ScientificResearcherDAO.getSientificsResearcher();
        if (!scientificResearcherQuery.isEmpty()) {
            scientificsResearcher.clear();
            scientificsResearcher.addAll(scientificResearcherQuery);
            scientificsTableView.setItems(scientificsResearcher);
        }
    }
    private void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
        try {
            Parent root = loader.load();
            FXMLMainMenuController mainMenuController = loader.getController();
            mainMenuController.configureView(user);
            Stage stage = (Stage) scientificsTableView.getScene().getWindow();
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
            deleteScientificButton.setVisible(true);
        } 
    }

    @FXML
    private void editButtonClick(ActionEvent event) throws SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLScientificResearcher.fxml"));
        String username = scientificsTableView.getSelectionModel().getSelectedItem().getUsername();
        try {
            Parent root = loader.load();
            FXMLScientificResearcherController scientificResearcherController = loader.getController();
            scientificResearcherController.configureView(Constants.EDIT_WINDOW_CODE, username,  null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerScientificResearcherView = new Scene(root);
            stage.setScene(registerScientificResearcherView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar investigador científico.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Edit scientific researcher.\" window...");
        }
    }

    @FXML
    private void deleteFloweringPlantButtonClick(ActionEvent event) throws SQLException{
        String name = scientificsTableView.getSelectionModel().getSelectedItem().getName();
        int responseCode = ScientificResearcherDAO.deleteScientificResearcher(name);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se eliminó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            loadScientificsResearcher();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        goToMainMenu();
    }
    public void updateFloweringPlants() {
        try {
            loadScientificsResearcher();
        } catch (SQLException ex) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.",
                    Alert.AlertType.ERROR);
        }
    }
    
}
