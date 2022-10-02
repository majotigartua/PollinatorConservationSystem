package pollinatorconservation.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pollinatorconservation.model.pojo.Pollinator;

public class FXMLPollinatorTemplateController implements Initializable {

    @FXML
    private ImageView pollinatorImageView;
    @FXML
    private Label pollinatorNameLabel;
    @FXML
    private Label pollinatorFamilyLabel;
    @FXML
    private VBox vBoxSpecies;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setPollinatorData(Pollinator pollinator) {
        Image image = new Image(pollinator.getImagePath());
        pollinatorImageView.setImage(image);
        pollinatorNameLabel.setText(pollinator.getGenericName());
        pollinatorFamilyLabel.setText(pollinator.getFamily().getName());
    }
    
}
