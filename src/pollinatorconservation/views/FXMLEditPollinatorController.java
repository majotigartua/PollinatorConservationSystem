package pollinatorconservation.views;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pollinatorconservation.model.dao.OrderDAO;
import pollinatorconservation.model.dao.PollinatorDAO;
import pollinatorconservation.model.pojo.Family;
import pollinatorconservation.model.pojo.Order;
import pollinatorconservation.model.pojo.Pollinator;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

/**
 * FXML Controller class
 *
 * @author alvaro
 */
public class FXMLEditPollinatorController implements Initializable {

    @FXML
    private TextField scintificNameTextField;
    @FXML
    private TextField genericNameTextField;
    @FXML
    private ComboBox<Order> orderComboBox;
    @FXML
    private ComboBox<Family> familyComboBox;
    @FXML
    private ImageView pollinatorImageView;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    
    private int typeOfViewToConfigure;
    
    private ObservableList<Order> orders;
    private ObservableList<Family> families;
    
    private File pollinatortImageFile;
    private Image pollinatorImage;
    PollinatorDAO pollinatorDAO = new PollinatorDAO();
    FXMLSelectedPollinatorController selectPollinator;
    private static String pollinatorName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Pollinator pollinatorOld = pollinatorDAO.getPollinator(pollinatorName);
            scintificNameTextField.setText(pollinatorOld.getScientificName());
            genericNameTextField.setText(pollinatorOld.getGenericName());
            descriptionTextArea.setText(pollinatorOld.getDescription());
            loadOrders();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }    

    @FXML
    private void editButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }

    void configureView(int REGISTRATION_WINDOW_CODE, String oldPollinatorName) {
        this.typeOfViewToConfigure = typeOfViewToConfigure;
        pollinatorName = oldPollinatorName;
        if (typeOfViewToConfigure == Constants.QUERY_WINDOW_CODE) {
            pollinatortImageFile = new File("src/pollinatorconservation/images/" + pollinatorName + ".jpg");
            pollinatorImageView.setOnMouseClicked(null);
        } else {
            pollinatortImageFile = new File("src/pollinatorconservation/images/default.png");
        }
        pollinatorImage = new Image(pollinatortImageFile.toURI().toString());
        pollinatorImageView.setImage(pollinatorImage);
    }

    private void loadOrders() throws SQLException{
        orders = FXCollections.observableArrayList();
        ArrayList<Order> query = OrderDAO.getOrders();
        orders.addAll(query);
        orderComboBox.setItems(orders);
        orderComboBox.valueProperty().addListener(new ChangeListener<Order>(){
            
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                if (newValue != null) {
                    try {
                        loadFamiliesByOrder(orderComboBox.getValue());
                    } catch (SQLException ex) {
                        Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                                + "Por favor, inténtelo más tarde.\n",
                                Alert.AlertType.ERROR);
                    }
                }
            }

            private void loadFamiliesByOrder(Order value) throws SQLException{
                
                
                
            }
        });
    }


    
}
