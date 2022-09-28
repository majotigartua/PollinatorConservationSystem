/*

 * Author: Sebastian Bello Trejo
 * Date: 25/09/2022
 * Description:
 */
package pollinatorconservation.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pollinatorconservation.pojo.Species;

/**
 *
 * @author sebtr
 */
public class FXMLMenuController implements Initializable {

    @FXML
    private GridPane gridPaneSpecies;
    @FXML
    private HBox hBoxSpecies;
    
    private List<Species> listSpeciesesCatalog;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listSpeciesesCatalog = new ArrayList<>(species());
        int column = 0, row = 1;
        try{
            for(Species species : listSpeciesesCatalog){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("FXMLSpecies.fxml"));
                VBox specieBox = loader.load();
                FXMLSpeciesController speciesController = loader.getController();
                speciesController.setSpeciesData(species);
                
                if(column == 6) {
                    column = 0;
                    ++row;
                }
                
                gridPaneSpecies.add(specieBox, column++, row);
                GridPane.setMargin(specieBox, new Insets(10));
                
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
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
    
    private List<Species> species() {
        List<Species> listSpecieses = new ArrayList<>();
        Species species = new Species();
        
        return listSpecieses;
    }
}
