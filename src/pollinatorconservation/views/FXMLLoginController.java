package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) throws IOException {
    }
    
    public boolean validateEmptyFields() {
        return (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) ? true : false;
    }
    
}