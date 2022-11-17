package pollinatorconservation.views;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import pollinatorconservation.interfaces.IScientificResearcher;
import pollinatorconservation.model.dao.ScientificResearcherDAO;
import pollinatorconservation.model.pojo.Role;
import pollinatorconservation.model.pojo.ScientificResearcher;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLScientificResearcherController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField professionalLicenseNumberTextField;
    @FXML
    private ImageView imageView;

    private File scientificResearcherImageFile;
    private Image scientificResearcherImage;
    private IScientificResearcher scientificResearcherInterface;

    int typeOfViewToConfigure;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(int typeOfViewToConfigure, String username, IScientificResearcher scientificResearcherInterface) throws SQLException {
        this.typeOfViewToConfigure = typeOfViewToConfigure;
        this.scientificResearcherInterface = scientificResearcherInterface;
        if (typeOfViewToConfigure == Constants.REGISTRATION_WINDOW_CODE) {
            scientificResearcherImageFile = new File("src/pollinatorconservation/images/default.png");
        } else {
            usernameTextField.setEditable(false);
            scientificResearcherImageFile = new File("src/pollinatorconservation/images/scientificresearchers/" + username + ".jpg");
            loadScientificResearcher(username);
        }
        scientificResearcherImage = new Image(scientificResearcherImageFile.toURI().toString());
        imageView.setImage(scientificResearcherImage);
    }

    @FXML
    private void addScientificResearcherImageClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar foto de perfil del investigador científico.");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Formato de intercambio de archivos JPEG (*.jpg, *.jpeg)", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scientificResearcherImageFile = fileChooser.showOpenDialog(stage);
        loadScientificResearcherImage();
    }

    private void loadScientificResearcher(String username) throws SQLException {
        ScientificResearcher scientificResearcher = ScientificResearcherDAO.getScientificResearcher(username);
        nameTextField.setText(scientificResearcher.getName());
        paternalSurnameTextField.setText(scientificResearcher.getPaternalSurname());
        maternalSurnameTextField.setText(scientificResearcher.getMaternalSurname());
        usernameTextField.setText(username);
        professionalLicenseNumberTextField.setText(scientificResearcher.getProfessionalLicenseNumber());
    }

    private void loadScientificResearcherImage() {
        if (scientificResearcherImageFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(scientificResearcherImageFile);
                scientificResearcherImage = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(scientificResearcherImage);
            } catch (IOException exception) {
                System.err.println("Error loading flowering plant image...");
            }
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) throws NoSuchAlgorithmException, IOException, SQLException {
        if (!validateEmptyFields()) {
            ScientificResearcher scientificResearcher = new ScientificResearcher();
            scientificResearcher.setName(nameTextField.getText());
            scientificResearcher.setPaternalSurname(paternalSurnameTextField.getText());
            scientificResearcher.setMaternalSurname(maternalSurnameTextField.getText());
            String username = usernameTextField.getText();
            username = username.toLowerCase().trim();
            scientificResearcher.setUsername(username);
            String password = Utilities.computeSHA256Hash(passwordField.getText());
            scientificResearcher.setPassword(password);
            scientificResearcher.setProfessionalLicenseNumber(professionalLicenseNumberTextField.getText());
            Role role = new Role();
            role.setIdRole(Constants.ID_SCIENTIFIC_RESEARCHER_ROLE);
            scientificResearcher.setRole(role);
            if (typeOfViewToConfigure == Constants.REGISTRATION_WINDOW_CODE) {
                registerScientificResearcher(scientificResearcher);
            } else {
                editScientificResearcher(scientificResearcher);
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean validateEmptyFields() {
        return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || usernameTextField.getText().isEmpty()
                || professionalLicenseNumberTextField.getText().isEmpty();
    }

    private void registerScientificResearcher(ScientificResearcher scientificResearcher) throws IOException {
        int responseCode = ScientificResearcherDAO.registerScientificResearcher(scientificResearcher);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                        Alert.AlertType.INFORMATION);
                registerScientificResearcherImage(scientificResearcher);
                closePopUpWindow();
                break;
            case Constants.SCIENTIFIC_RESEARCHER_ALREADY_REGISTERED:
                Utilities.showAlert("La información ingresada corresponde a un investigador científico que ya se encuentra registrado en el sistema.\n\n"
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

    private void registerScientificResearcherImage(ScientificResearcher scientificResearcher) throws IOException {
        String username = scientificResearcher.getUsername();
        File file = new File("src/pollinatorconservation/images/scientificresearchers/" + username + ".jpg");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(scientificResearcherImage, null);
        BufferedImage bufferedImageOnRGB = new BufferedImage(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.OPAQUE);
        Graphics2D graphicsOn2D = bufferedImageOnRGB.createGraphics();
        graphicsOn2D.drawImage(bufferedImage, 0, 0, null);
        ImageIO.write(bufferedImageOnRGB, "jpg", file);
        graphicsOn2D.dispose();
    }

    private void editScientificResearcher(ScientificResearcher scientificResearcher) throws SQLException, IOException {
        int responseCode = ScientificResearcherDAO.editScientificResearcher(scientificResearcher);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            registerScientificResearcherImage(scientificResearcher);
            scientificResearcherInterface.updateScientificResearchers();
            closePopUpWindow();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    private void closePopUpWindow() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }
    
}