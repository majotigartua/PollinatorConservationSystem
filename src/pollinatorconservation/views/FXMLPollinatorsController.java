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
import pollinatorconservation.interfaces.IPollinator;
import pollinatorconservation.model.dao.PollinatorDAO;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLPollinatorsController implements Initializable, IPollinator {

    @FXML
    private Label instructionLabel;
    @FXML
    private TableView<Pollinator> pollinatorTableView;
    @FXML
    private TableColumn scientificNameTableColumn;
    @FXML
    private TableColumn genericNameTableColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button deletePollinatorButton;
    @FXML
    private Button consultButton;

    private User user;
    private ObservableList<Pollinator> pollinators;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editButton.disableProperty().bind(Bindings.isEmpty(pollinatorTableView.getSelectionModel().getSelectedItems()));
        deletePollinatorButton.disableProperty().bind(Bindings.isEmpty(pollinatorTableView.getSelectionModel().getSelectedItems()));
        consultButton.disableProperty().bind(Bindings.isEmpty(pollinatorTableView.getSelectionModel().getSelectedItems()));
        configurePollinatorsTableView();
        try {
            loadPollinators();
        } catch (SQLException ex) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.",
                    Alert.AlertType.ERROR);
            goToMainMenu();
        }
    }

    public void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
        try {
            Parent root = loader.load();
            FXMLMainMenuController mainMenuController = loader.getController();
            mainMenuController.configureView(user);
            Stage stage = (Stage) pollinatorTableView.getScene().getWindow();
            Scene mainMenuView = new Scene(root);
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Main Menu\" window...");
        }
    }

    private void configurePollinatorsTableView() {
        pollinators = FXCollections.observableArrayList();
        scientificNameTableColumn.setCellValueFactory(new PropertyValueFactory("scientificName"));
        genericNameTableColumn.setCellValueFactory(new PropertyValueFactory("genericName"));
    }

    private void loadPollinators() throws SQLException {
        pollinatorTableView.getItems().clear();
        ArrayList<Pollinator> pollinatorsQuery = PollinatorDAO.getPollinators();
        if (!pollinatorsQuery.isEmpty()) {
            pollinators.clear();
            pollinators.addAll(pollinatorsQuery);
            pollinatorTableView.setItems(pollinators);
        }
    }

    public void configureView(User user, int typeOfViewToConfigure) {
        this.user = user;
        if (typeOfViewToConfigure == Constants.EDIT_WINDOW_CODE) {
            editButton.setVisible(true);
            deletePollinatorButton.setVisible(true);
        } else {
            instructionLabel.setText("Seleccione una especie polinizadora y dé clic en el botón de \"Consultar\"");
            consultButton.setVisible(true);
        }
    }

    @FXML
    private void editButtonClick(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinator.fxml"));
        String scientificName = pollinatorTableView.getSelectionModel().getSelectedItem().getScientificName();
        try {
            Parent root = loader.load();
            FXMLPollinatorController pollinatorController = loader.getController();
            pollinatorController.configureView(Constants.EDIT_WINDOW_CODE, scientificName, this);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar especie polinizadora.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Edit flowering plant.\" window...");
        }
    }

    @FXML
    private void deletePollinatorButtonClick(ActionEvent event) throws SQLException {
        String scientificName = pollinatorTableView.getSelectionModel().getSelectedItem().getScientificName();
        int responseCode = PollinatorDAO.deletePollinator(scientificName);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se eliminó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            loadPollinators();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void consultButtonClick(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPollinator.fxml"));
        String scientificName = pollinatorTableView.getSelectionModel().getSelectedItem().getScientificName();
        try {
            Parent root = loader.load();
            FXMLPollinatorController pollinatorController = loader.getController();
            pollinatorController.configureView(Constants.QUERY_WINDOW_CODE, scientificName, null);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
            stage.setResizable(false);
            Scene registerFloweringPlantView = new Scene(root);
            stage.setScene(registerFloweringPlantView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consultar especie polinizadora.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Consult flowering plant.\" window...");
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        goToMainMenu();
    }

    @Override
    public void updatePollinators() {
        try {
            loadPollinators();
        } catch (SQLException ex) {
            Utilities.showAlert("No hay conexión con la base de datos. Por favor inténtelo más tarde.",
                    Alert.AlertType.ERROR);
        }
    }

}
