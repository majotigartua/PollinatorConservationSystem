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
import pollinatorconservation.model.dao.PollinatorDAO;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author alvaro
 */
public class FXMLSelectedPollinatorController implements Initializable {

    @FXML
    private TableView<Pollinator> pollinatorTable;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    
    PollinatorDAO pollinatorDAO = new PollinatorDAO();
    private int typeOfViewToConfigure;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deleteButton.disableProperty().bind(Bindings.isEmpty(pollinatorTable.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(Bindings.isEmpty(pollinatorTable.getSelectionModel().getSelectedItems()));
        try {
            loadPollinators();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }    

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }

    @FXML
    private void deleteButtonClick(ActionEvent event) throws SQLException, IOException{
        String scientificName = pollinatorTable.getSelectionModel().getSelectedItem().toString();
        deletePollinator(scientificName);
    }

    @FXML
    private void editButtonClick(ActionEvent event) {
        String scientificName = pollinatorTable.getSelectionModel().getSelectedItem().toString();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditPollinator.fxml"));
        try {
            Parent root = loader.load();
            FXMLEditPollinatorController editPollinatorController = loader.getController();
            editPollinatorController.configureView(Constants.REGISTRATION_WINDOW_CODE, scientificName);
            Stage stage = new Stage();
            Scene editPollinatorView = new Scene(root);
            stage.setScene(editPollinatorView);
            stage.setTitle("Editar especie polinizadora");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Edit Pollinator\" window...");
        }
    }

    private void loadPollinators() throws SQLException{
        ArrayList<Pollinator> pollinator = new ArrayList<Pollinator>();
        pollinator = (ArrayList<Pollinator>) pollinatorDAO.getPollinators();
        ObservableList<Pollinator> pollinatorsTable = FXCollections.observableArrayList(pollinator);
        TableColumn columnTitleGenericName = new TableColumn("Nombre genérico");
        TableColumn columnTitleScientificName = new TableColumn("Nombre científico");
        pollinatorTable.getColumns().addAll(columnTitleScientificName, columnTitleGenericName);
        columnTitleGenericName.setCellValueFactory(new PropertyValueFactory<Pollinator,String>("genericName"));
        columnTitleScientificName.setCellValueFactory(new PropertyValueFactory<Pollinator,String>("scientificName"));
        pollinatorTable.setItems(pollinatorsTable);
    }

    private void closePopUpWindow() {
        Stage stage = (Stage) pollinatorTable.getScene().getWindow();
        stage.close();
    }

    private void deletePollinator(String scientificName) throws SQLException, IOException{
        int responseCode = pollinatorDAO.deletePollinator(scientificName);
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
