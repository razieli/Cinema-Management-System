package il.ac.haifa.cs.sweng.cms;
import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.LoginResponse;
import javafx.application.Platform;
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
            this.checkLogin(App.getUserName(), App.getPass());
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

    public void checkLogin(String username, String pass){
        App.getOcsfClient(this).tryLogin(username, pass);
    }

    public void onReplyReceived(LoginResponse response) {
        if (response.getStatus() == ResponseStatus.DeclinedUser) {
            App.setUserPermission(-1);
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, null, "Wrong Username!"));
        }
        else if (response.getStatus() == ResponseStatus.DeclinedPass) {
            App.setUserPermission(-1);
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, null, "Wrong Password!"));
        }
        else if (response.getStatus() == ResponseStatus.Customer) {
            App.setUserPermission(0);
        }
        else if (response.getStatus() == ResponseStatus.CustomerService) {
            App.setUserPermission(1);
        }
        else if (response.getStatus() == ResponseStatus.ContentManager) {
            App.setUserPermission(2);
        }
        else if (response.getStatus() == ResponseStatus.BranchManager) {
            App.setUserPermission(3);
        }
        else if (response.getStatus() == ResponseStatus.Administrator) {
            App.setUserPermission(4);
        }
        App.setUser(response.getUser());
        int permission = App.getUserPermission();

        if(permission > 0){
            try {
                App.setRoot("EmployeeHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (permission == 0) {
            try {
                App.setRoot("CustomerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // TODO: Show "Unidentified response".
    }

    private void showAlert(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name().substring(0, 1).toUpperCase() + alertType.name().substring(1).toLowerCase());
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
