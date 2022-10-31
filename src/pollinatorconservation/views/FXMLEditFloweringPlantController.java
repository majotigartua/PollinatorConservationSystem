/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pollinatorconservation.views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import pollinatorconservation.model.dao.CladeDAO;
import pollinatorconservation.model.dao.FamilyDAO;
import pollinatorconservation.model.dao.FloweringPlantDAO;
import pollinatorconservation.model.pojo.Clade;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLEditFloweringPlantController implements Initializable {

    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField scintificNameTextField;
    @FXML
    private TextField genericNameTextField;
    @FXML
    private ComboBox<Clade> cladeComboBox;
    @FXML
    private ComboBox<Family> familyComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane editFloweringPlantPane;
    
    private int typeOfViewToConfigure;

    private ObservableList<Clade> clades;
    private ObservableList<Family> families;

    private File floweringPlantImageFile;
    private Image floweringPlantImage;
    FloweringPlantDAO floweringPlantDao = new FloweringPlantDAO();
    FXMLSelectedFloweringPlantController selectFloweringPlant;
    private static String floweringPlantName;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            FloweringPlant floweringPlantOld = floweringPlantDao.getFloweringPlant(floweringPlantName);
            scintificNameTextField.setText(floweringPlantOld.getScientificName());
            genericNameTextField.setText(floweringPlantOld.getGenericName());
            descriptionTextArea.setText(floweringPlantOld.getDescription());
            loadClades();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }
    public void configureView(int typeOfViewToConfigure, String oldFloweringPlantName) {
        this.typeOfViewToConfigure = typeOfViewToConfigure;
        floweringPlantName = oldFloweringPlantName;
        if (typeOfViewToConfigure == Constants.QUERY_WINDOW_CODE) {
            floweringPlantImageFile = new File("src/pollinatorconservation/images/" + floweringPlantName + ".jpg");
            imageView.setOnMouseClicked(null);
        } else {
            floweringPlantImageFile = new File("src/pollinatorconservation/images/default.png");
        }
        floweringPlantImage = new Image(floweringPlantImageFile.toURI().toString());
        imageView.setImage(floweringPlantImage);
    }
    /**
     * Initializes the controller class.
     */  
    private void loadClades() throws SQLException {
        clades = FXCollections.observableArrayList();
        ArrayList<Clade> query = CladeDAO.getClades();
        clades.addAll(query);
        cladeComboBox.setItems(clades);
        cladeComboBox.valueProperty().addListener(new ChangeListener<Clade>() {
            @Override
            public void changed(ObservableValue<? extends Clade> observable, Clade oldValue, Clade newValue) {
                if (newValue != null) {
                    try {
                        loadFamiliesByClade(cladeComboBox.getValue());
                    } catch (SQLException ex) {
                        Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                                + "Por favor, inténtelo más tarde.\n",
                                Alert.AlertType.ERROR);
                    }
                }
            }
        });
    }
    
    private void loadFamiliesByClade(Clade clade) throws SQLException {
        families = FXCollections.observableArrayList();
        ArrayList<Family> query = FamilyDAO.getFamiliesByClade(clade);
        families.addAll(query);
        familyComboBox.setItems(families);
    }
    
    
    @FXML
    private void editButtonClick(ActionEvent event) throws IOException, SQLException{
        if (!validateEmptyFields()) {
            FloweringPlant floweringPlant = new FloweringPlant();
            floweringPlant.setScientificName(scintificNameTextField.getText());
            floweringPlant.setGenericName(genericNameTextField.getText());
            floweringPlant.setDescription(descriptionTextArea.getText());
            floweringPlant.setFamily(familyComboBox.getValue());
            if (typeOfViewToConfigure == Constants.REGISTRATION_WINDOW_CODE) {
                editFloweringPlant(floweringPlant, floweringPlantName);
            } else {
                // PENDING.
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }
    
    private void editFloweringPlantImage(FloweringPlant floweringPlant) throws IOException {
        File file = new File("src/pollinatorconservation/images/floweringplants/"
                + floweringPlant.getScientificName() + ".jpg");
        file.delete();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(floweringPlantImage, null);
        ImageIO.write(bufferedImage, "jpg", file);
    }
    private void editFloweringPlant(FloweringPlant floweringPlant, String floweringPlantName) throws SQLException, IOException {
        int responseCode = floweringPlantDao.editFloweringPlant(floweringPlant, floweringPlantName);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                        Alert.AlertType.INFORMATION);
                editFloweringPlantImage(floweringPlant);
                closePopUpWindow();
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }

    

    @FXML
    private void deleteButtonClick(ActionEvent event) {
    }
     private boolean validateEmptyFields() {
        return (scintificNameTextField.getText().isEmpty()
                || genericNameTextField.getText().isEmpty()
                || descriptionTextArea.getText().isEmpty()
                || cladeComboBox.getSelectionModel().isEmpty()
                || familyComboBox.getSelectionModel().isEmpty())
                || floweringPlantImageFile.getName().equals("default.png");
    }
    private void closePopUpWindow() {
        Stage stage = (Stage) scintificNameTextField.getScene().getWindow();
        stage.close();
    }
}
