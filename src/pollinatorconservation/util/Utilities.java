package pollinatorconservation.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.Alert;
import javax.xml.bind.DatatypeConverter;

public class Utilities {

    public static void showAlert(String message, Alert.AlertType type) {
        Alert popUpWindow = new Alert(type);
        popUpWindow.setTitle(null);
        popUpWindow.setHeaderText(null);
        popUpWindow.setContentText(message);
        popUpWindow.showAndWait();

    }

    public static String computeSHA256Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));    
        return (DatatypeConverter.printHexBinary(bytes).toLowerCase());
    }
    
}