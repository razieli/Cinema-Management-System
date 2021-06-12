package il.ac.haifa.cs.sweng.cms;
import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UserLoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Text userName;

    @FXML
    private TextField userText;

    @FXML
    private Text password;

    @FXML
    private TextField passText;

    @FXML
    private Button connectBtn;

    @FXML
    private Button networkBtn;

    @FXML
    void switchToConnectionSet(ActionEvent event) {
        try {
            App.setRoot("ConnectionSettings.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tryToConnect(ActionEvent event) throws IOException {
        App.setUser(userText.getText());
        App.setPass(passText.getText());
        int val_connection = App.connectToServer();
        if (val_connection == 1) {
            App.checkLogin();
        }
        else if (val_connection == -2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Already Connected");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Connection Failed");
            alert.showAndWait();
        }
    }
}
