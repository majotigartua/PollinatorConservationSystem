package pollinatorconservation.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import pollinatorconservation.model.pojo.User;

public class FXMLMainMenuController implements Initializable {
    
    private static User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(User user) {
        this.user = user;
    }

}