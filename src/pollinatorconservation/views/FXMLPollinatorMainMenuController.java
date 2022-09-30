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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pollinatorconservation.model.pojo.Pollinator;

public class FXMLPollinatorMainMenuController implements Initializable {

    @FXML
    private GridPane pollinatorsGridPane;
    @FXML
    private HBox pollinatorsHBox;
    
    private List<Pollinator> pollinatorsCatalogList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pollinatorsCatalogList = new ArrayList<>(pollinators());
        int column = 0, row = 1;
        try{
            for(Pollinator pollinators : pollinatorsCatalogList){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("FXMLPollinator.fxml"));
                VBox pollinatorVBox = loader.load();
                FXMLPollinatorController pollinatorController = loader.getController();
                pollinatorController.setPollinatorData(pollinators);
                if(column == 6) {
                    column = 0;
                    ++row;
                }
                pollinatorsGridPane.add(pollinatorVBox, column++, row);
                GridPane.setMargin(pollinatorVBox, new Insets(10));
                
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }    

    @FXML
    private void pressKeySearch(KeyEvent event) {
    }

    
    private List<Pollinator> pollinators() {
        List<Pollinator> pollinatorsList = new ArrayList<>();
        Pollinator pollinator = new Pollinator();
        
        return pollinatorsList;
    }
}