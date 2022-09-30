package pollinatorconservation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PollinatorConservation extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/FXMLMainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}