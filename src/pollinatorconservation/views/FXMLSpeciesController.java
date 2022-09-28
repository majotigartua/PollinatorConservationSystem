/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pollinatorconservation.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pollinatorconservation.pojo.Species;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class FXMLSpeciesController implements Initializable {

    @FXML
    private ImageView imageViewSpecies;
    @FXML
    private Label textFieldSpeciesName;
    @FXML
    private Label textFieldSpeciesFamily;
    @FXML
    private VBox vBoxSpecies;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setSpeciesData(Species species) {
        Image image = new Image(species.getImagePath());
        imageViewSpecies.setImage(image);
        textFieldSpeciesName.setText(species.getName());
        textFieldSpeciesFamily.setText(species.getFamily());
    }
    
}
