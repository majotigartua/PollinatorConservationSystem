/*

 * Author: Sebastian Bello Trejo
 * Date: 25/09/2022
 * Description:
 */
package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author sebtr
 */
public class FXMLMenuController implements Initializable {

    @FXML
    private GridPane gridPaneSpecies;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pressKeySearch(KeyEvent event) {
    }

    @FXML
    private void clickLabelLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
        
        stage.setScene(scene);
        stage.setTitle("Ingresar");
        stage.show();
    }
    
}
