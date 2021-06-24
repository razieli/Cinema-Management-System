package il.ac.haifa.cs.sweng.cms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class OperationalReportsController {
    private int permission= App.getUserPermission();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button back;

    @FXML
    void backBtn(ActionEvent event) {
       if (permission == 3 ){//Employee
            try {
                App.setRoot("CinemaManagerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if ( permission == 4){//Employee
            try {
                App.setRoot("GeneralManagerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
        {
            try {
                App.disconnect();
                App.setRoot("UserLogin.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name().substring(0, 1).toUpperCase() + alertType.name().substring(1).toLowerCase());
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
