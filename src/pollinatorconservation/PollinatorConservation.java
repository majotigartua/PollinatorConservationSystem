/*

 * Author: Sebastian Bello Trejo
 * Date: 25/09/2022
 * Description: Project start class.
 */
package pollinatorconservation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author sebtr
 */
public class PollinatorConservation extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/FXMLMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(PollinatorConservation.class.getResourceAsStream("images/inecol.png")));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
