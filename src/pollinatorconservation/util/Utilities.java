package pollinatorconservation.util;

import javafx.scene.control.Alert;

public class Utilities {

    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert popUpWindow = new Alert(type);
        popUpWindow.setHeaderText(null);
        popUpWindow.setTitle(title);
        popUpWindow.setContentText(message);
        popUpWindow.showAndWait();

    }
}