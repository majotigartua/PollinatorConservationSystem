package pollinatorconservation.views;

import java.awt.Graphics2D;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import pollinatorconservation.interfaces.IFloweringPlant;
import pollinatorconservation.model.dao.CladeDAO;
import pollinatorconservation.model.dao.FamilyDAO;
import pollinatorconservation.model.dao.FloweringPlantDAO;
import pollinatorconservation.model.pojo.Clade;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.FloweringPlant;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLFloweringPlantController implements Initializable {

    @FXML
    private Label instructionLabel;
    @FXML
    private TextField scientificNameTextField;
    @FXML
    private TextField genericNameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<Clade> cladeComboBox;
    @FXML
    private ComboBox<Family> familyComboBox;
    @FXML
    private Button acceptButton;

    private int typeOfViewToConfigure;

    private ObservableList<Clade> clades;
    private ObservableList<Family> families;

    private File floweringPlantImageFile;
    private Image floweringPlantImage;
    
    private IFloweringPlant floweringPlantInterface;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadClades();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

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

    public void configureView(int typeOfViewToConfigure, String scientificName, IFloweringPlant floweringPlantInterface) throws SQLException {
        this.typeOfViewToConfigure = typeOfViewToConfigure;
        this.floweringPlantInterface = floweringPlantInterface;
        switch (typeOfViewToConfigure) {
            case Constants.REGISTRATION_WINDOW_CODE:
                floweringPlantImageFile = new File("src/pollinatorconservation/images/default.png");
                break;
            case Constants.EDIT_WINDOW_CODE:
                floweringPlantImageFile = new File("src/pollinatorconservation/images/floweringplants/" + getFloweringPlantImageName(scientificName) + ".jpg");
                scientificNameTextField.setEditable(false);
                loadFloweringPlant(scientificName);
                break;
            case Constants.QUERY_WINDOW_CODE:
                floweringPlantImageFile = new File("src/pollinatorconservation/images/floweringplants/" + getFloweringPlantImageName(scientificName) + ".jpg");
                instructionLabel.setVisible(false);
                scientificNameTextField.setEditable(false);
                genericNameTextField.setEditable(false);
                cladeComboBox.setDisable(true);
                familyComboBox.setDisable(true);
                descriptionTextArea.setEditable(false);
                imageView.setOnMouseClicked(null);
                acceptButton.setVisible(false);
                loadFloweringPlant(scientificName);
                break;
        }
        floweringPlantImage = new Image(floweringPlantImageFile.toURI().toString());
        imageView.setImage(floweringPlantImage);
    }

    private String getFloweringPlantImageName(String scientificName) {
        return scientificName.toLowerCase().replaceAll("\\s", "");
    }

    private void loadFloweringPlant(String scientificName) throws SQLException {
        FloweringPlant floweringPlant = FloweringPlantDAO.getFloweringPlant(scientificName);
        scientificNameTextField.setText(scientificName);
        genericNameTextField.setText(floweringPlant.getGenericName());
        descriptionTextArea.setText(floweringPlant.getDescription());
        int cladeComboBoxIndex = getCladeComboBoxIndex(floweringPlant.getFamily().getClade().getIdClade());
        cladeComboBox.getSelectionModel().select(cladeComboBoxIndex);
        int familyComboBoxIndex = getFamilyComboBoxIndex(floweringPlant.getFamily().getIdFamily());
        familyComboBox.getSelectionModel().select(familyComboBoxIndex);
    }

    private int getFamilyComboBoxIndex(int idFamily) {
        int familyComboBoxIndex = 0;
        for (int family = 0; family < families.size(); family++) {
            if (families.get(family).getIdFamily() == idFamily) {
                return familyComboBoxIndex = family;
            }
        }
        return familyComboBoxIndex;
    }

    private int getCladeComboBoxIndex(int idClade) {
        int cladeComboBoxIndex = 0;
        for (int clade = 0; clade < clades.size(); clade++) {
            if (clades.get(clade).getIdClade() == idClade) {
                return cladeComboBoxIndex = clade;
            }
        }
        return cladeComboBoxIndex;
    }

    @FXML
    private void addFloweringPlantImageClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de referencia de la planta florífera.");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Formato de intercambio de archivos JPEG (*.jpg, *.jpeg)", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        floweringPlantImageFile = fileChooser.showOpenDialog(stage);
        loadFloweringPlantImage();
    }

    private void loadFloweringPlantImage() {
        if (floweringPlantImageFile != null) {
            floweringPlantImage = new Image(floweringPlantImageFile.toURI().toString());
            imageView.setImage(floweringPlantImage);
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) throws IOException, SQLException {
        if (!validateEmptyFields()) {
            FloweringPlant floweringPlant = new FloweringPlant();
            floweringPlant.setScientificName(scientificNameTextField.getText());
            floweringPlant.setGenericName(genericNameTextField.getText());
            floweringPlant.setDescription(descriptionTextArea.getText());
            floweringPlant.setFamily(familyComboBox.getValue());
            if (typeOfViewToConfigure == Constants.REGISTRATION_WINDOW_CODE) {
                registerFloweringPlant(floweringPlant);
            } else {
                editFloweringPlant(floweringPlant);
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean validateEmptyFields() {
        return scientificNameTextField.getText().isEmpty()
                || genericNameTextField.getText().isEmpty()
                || descriptionTextArea.getText().isEmpty()
                || cladeComboBox.getSelectionModel().isEmpty()
                || familyComboBox.getSelectionModel().isEmpty();
    }

    private void registerFloweringPlant(FloweringPlant floweringPlant) throws SQLException, IOException {
        int responseCode = FloweringPlantDAO.registerFloweringPlant(floweringPlant);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                        Alert.AlertType.INFORMATION);
                registerFloweringPlantImage(floweringPlant);
                closePopUpWindow();
                break;
            case Constants.SPECIES_ALREADY_REGISTERED:
                Utilities.showAlert("La información ingresada corresponde a una planta florífera que ya se encuentra registrada en el sistema.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }

    private void closePopUpWindow() {
        Stage stage = (Stage) scientificNameTextField.getScene().getWindow();
        stage.close();
    }

    private void editFloweringPlant(FloweringPlant floweringPlant) throws SQLException, IOException {
        int responseCode = FloweringPlantDAO.editFloweringPlant(floweringPlant);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            registerFloweringPlantImage(floweringPlant);
            floweringPlantInterface.updateFloweringPlants();
            closePopUpWindow();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    private void registerFloweringPlantImage(FloweringPlant floweringPlant) throws IOException {
        String scientificName = floweringPlant.getScientificName();
        File file = new File("src/pollinatorconservation/images/floweringplants/" + getFloweringPlantImageName(scientificName) + ".jpg");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(floweringPlantImage, null);
        BufferedImage bufferedImageOnRGB = new BufferedImage(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.OPAQUE);
        Graphics2D graphicsOn2D = bufferedImageOnRGB.createGraphics();
        graphicsOn2D.drawImage(bufferedImage, 0, 0, null);
        ImageIO.write(bufferedImageOnRGB, "jpg", file);
        graphicsOn2D.dispose();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }

}