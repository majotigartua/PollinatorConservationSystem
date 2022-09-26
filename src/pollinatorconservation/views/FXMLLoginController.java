/*

 * Author: Sebastian Bello Trejo
 * Date: 25/09/2022
 * Description:
 */
package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Label labelFailedLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickButtonLogin(ActionEvent event) {
        validateLogin();
    }

    @FXML
    private void clickButtonCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLMenu.fxml")));
        
        stage.setScene(scene);
        stage.setTitle("Sistema para la Divulgación Científica sobre la Conservación de Polinizadores");
        stage.show();
    }
    
    public boolean validateEmptyFields() {
        return (textFieldUsername.getText().isEmpty() || passwordFieldPassword.getText().isEmpty()) ? true : false;
    }
    
    public void validateLogin() {
        if (validateEmptyFields()) {
            labelFailedLogin.setVisible(true);
        }
    }
    
}
