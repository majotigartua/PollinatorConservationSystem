package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pollinatorconservation.model.dao.UserDAO;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void loginButtonClick(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else {
            try {
                password = Utilities.computeSHA256Hash(password);
                login(username, password);
            } catch (SQLException exception) {
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo nuevamente más tarde.\n",
                        Alert.AlertType.ERROR);
            } catch (NoSuchAlgorithmException exception) {
                System.err.println("Error encrypting the password with SHA-256...");;
            }
        }
    }

    public void login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        User user = UserDAO.login(username, password);
        switch (user.getResponseCode()) {
            case Constants.CORRECT_OPERATION_CODE:
                goToMainMenu(user);
                break;
            case Constants.INVALID_ENTERED_DATA_CODE:
                Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
                passwordField.setText("");
                break;
            case Constants.NO_DATABASE_CONNECTION_CODE:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }

    @FXML
    private void enterAsGuestButtonClick(ActionEvent event) {
        goToMainMenu(null);
    }

    public void goToMainMenu(User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
        try {
            Parent root = loader.load();
            FXMLMainMenuController mainMenuController = loader.getController();
            mainMenuController.configureView(user);
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            Scene mainMenuView = new Scene(root);
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("Error loading the \"Main Menu\" window...");
        }
    }
}