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
import pollinatorconservation.interfaces.IPollinator;
import pollinatorconservation.model.dao.FamilyDAO;
import pollinatorconservation.model.dao.OrderDAO;
import pollinatorconservation.model.dao.PollinatorDAO;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.Order;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLPollinatorController implements Initializable {

    @FXML
    private Label instructionLabel;
    @FXML
    private TextField scientificNameTextField;
    @FXML
    private TextField genericNameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ComboBox<Order> orderComboBox;
    @FXML
    private ComboBox<Family> familyComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private Button acceptButton;

    private int typeOfViewToConfigure;

    private ObservableList<Order> orders;
    private ObservableList<Family> families;

    private File pollinatorImageFile;
    private Image pollinatorImage;

    private IPollinator pollinatorInterface;

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
        orders = FXCollections.observableArrayList();
        ArrayList<Order> query = OrderDAO.getOrders();
        orders.addAll(query);
        orderComboBox.setItems(orders);
        orderComboBox.valueProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                if (newValue != null) {
                    try {
                        loadFamiliesByOrder(orderComboBox.getValue());
                    } catch (SQLException ex) {
                        Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                                + "Por favor, inténtelo más tarde.\n",
                                Alert.AlertType.ERROR);
                    }
                }
            }
        });
    }

    private void loadFamiliesByOrder(Order order) throws SQLException {
        families = FXCollections.observableArrayList();
        ArrayList<Family> query = FamilyDAO.getFamiliesByOrder(order);
        families.addAll(query);
        familyComboBox.setItems(families);
    }

    public void configureView(int typeOfViewToConfigure, String scientificName, IPollinator pollinatorInterface) throws SQLException {
        this.typeOfViewToConfigure = typeOfViewToConfigure;
        this.pollinatorInterface = pollinatorInterface;
        switch (typeOfViewToConfigure) {
            case Constants.REGISTRATION_WINDOW_CODE:
                pollinatorImageFile = new File("src/pollinatorconservation/images/default.png");
                break;
            case Constants.EDIT_WINDOW_CODE:
                pollinatorImageFile = new File("src/pollinatorconservation/images/pollinators/" + getPollinatorImageName(scientificName) + ".jpg");
                scientificNameTextField.setEditable(false);
                loadPollinator(scientificName);
                break;
            default:
                pollinatorImageFile = new File("src/pollinatorconservation/images/pollinators/" + getPollinatorImageName(scientificName) + ".jpg");
                instructionLabel.setVisible(false);
                scientificNameTextField.setEditable(false);
                genericNameTextField.setEditable(false);
                familyComboBox.setDisable(true);
                orderComboBox.setDisable(true);
                descriptionTextArea.setEditable(false);
                imageView.setOnMouseClicked(null);
                acceptButton.setVisible(false);
                loadPollinator(scientificName);
                break;
        }
        pollinatorImage = new Image(pollinatorImageFile.toURI().toString());
        imageView.setImage(pollinatorImage);
    }

    private String getPollinatorImageName(String scientificName) {
        return scientificName.toLowerCase().replaceAll("\\s", "");
    }

    private void loadPollinator(String scientificName) throws SQLException {
        Pollinator pollinator = PollinatorDAO.getPollinator(scientificName);
        scientificNameTextField.setText(scientificName);
        genericNameTextField.setText(pollinator.getGenericName());
        descriptionTextArea.setText(pollinator.getDescription());
        int orderComboBoxIndex = getOrderComboBoxIndex(pollinator.getFamily().getOrder().getIdOrder());
        orderComboBox.getSelectionModel().select(orderComboBoxIndex);
        int familyComboBoxIndex = getFamilyComboBoxIndex(pollinator.getFamily().getIdFamily());
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

    private int getOrderComboBoxIndex(int idOrder) {
        int orderComboBoxIndex = 0;
        for (int order = 0; order < orders.size(); order++) {
            if (orders.get(order).getIdOrder() == idOrder) {
                return orderComboBoxIndex = order;
            }
        }
        return orderComboBoxIndex;
    }

    @FXML
    private void addPollinatorImageClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de referencia de la especie polinizadora.");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Formato de intercambio de archivos JPEG (*.jpg, *.jpeg)", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        pollinatorImageFile = fileChooser.showOpenDialog(stage);
        loadPollinatorImage();
    }

    private void loadPollinatorImage() {
        if (pollinatorImageFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(pollinatorImageFile);
                pollinatorImage = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(pollinatorImage);
            } catch (IOException exception) {
                System.err.println("Error loading flowering plant image...");
            }
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) throws IOException, SQLException {
        if (!validateEmptyFields()) {
            Pollinator pollinator = new Pollinator();
            pollinator.setScientificName(scientificNameTextField.getText());
            pollinator.setGenericName(genericNameTextField.getText());
            pollinator.setDescription(descriptionTextArea.getText());
            pollinator.setFamily(familyComboBox.getValue());
            if (typeOfViewToConfigure == Constants.REGISTRATION_WINDOW_CODE) {
                registerPollinator(pollinator);
            } else {
                editPollinator(pollinator);
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
                || orderComboBox.getSelectionModel().isEmpty()
                || familyComboBox.getSelectionModel().isEmpty();
    }

    private void registerPollinator(Pollinator pollinator) throws SQLException, IOException {
        int responseCode = PollinatorDAO.registerPollinator(pollinator);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                        Alert.AlertType.INFORMATION);
                registerPollinatorImage(pollinator);
                closePopUpWindow();
                break;
            case Constants.SPECIES_ALREADY_REGISTERED:
                Utilities.showAlert("La información ingresada corresponde a una especie polinizadora que ya se encuentra registrada en el sistema.\n\n"
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

    private void editPollinator(Pollinator pollinator) throws SQLException, IOException {
        int responseCode = PollinatorDAO.editPollinator(pollinator);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            registerPollinatorImage(pollinator);
            pollinatorInterface.updatePollinators();
            closePopUpWindow();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    private void registerPollinatorImage(Pollinator pollinator) throws IOException {
        String scientificName = pollinator.getScientificName();
        File file = new File("src/pollinatorconservation/images/pollinators/" + getPollinatorImageName(scientificName) + ".jpg");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(pollinatorImage, null);
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