package il.ac.haifa.cs.sweng.cms;
import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.LoginResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.MailResponse;
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
            // new purchase
            App.getOcsfClient(this).sendMail(
                    "galuk3@gmail.com",
                    "Order Confirmed",
                    "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello Dear Customer,</i></h1><br>" +
                            "<br><h2 style=\"color:black;\">Thanks for your purchase!</h2>" +
                            "<br><h3 style=\"color:black;\">Your order (3292384) is confirmed.</h3> "+
                            "<br><table border='1' dir=\"ltr\">\n" +
                            "    <tr>\n" +
                            "      <td>theater</td>\n" +
                            "      <td>movie</td>\n" +
                            "      <td>num of tickets</td>\n" +
                            "      <td>places</td>\n" +
                            "    </tr>\n" +
                            "    <tr>\n" +
                            "      <td>Haifa</td>\n" +
                            "      <td>Batman</td>\n" +
                            "      <td>2</td>\n" +
                            "      <td>row 2 column 3, row 2 column 4</td>\n" +
                            "    </tr></table dir=\"ltr\">" +
                            "</bdo>");


            // link is ready in one hour
            App.getOcsfClient(this).sendMail(
                    "galuk3@gmail.com",
                    "Movie link is almost ready",
                    "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello Dear Customer,</i></h1><br>" +
                            "<br><h2 style=\"color:black;\">The movie link you purchased will be ready in less than one hour!</h2>" +
                            "</bdo>");

            // canceled screening message
            App.getOcsfClient(this).sendMail(
                    "galuk3@gmail.com",
                    "Screening Canceled",
                    "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello Dear Customer,</i></h1><br>" +
                            "<br><h2 style=\"color:black;\">Unfortunately, the screening of the movie " +
                            "you bought tickets for is canceled. </h2>" +
                            "<br><h2 style=\"color:black;\">your bank account will receive a compensation of 50$ shortly." +
                            "</bdo>");

            // changed screening message
            App.getOcsfClient(this).sendMail(
                    "galuk3@gmail.com",
                    "Screening seats Changed due to Purple Badge Rules",
                    "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello Dear Customer,</i></h1><br>" +
                            "<br><h2 style=\"color:black;\">Unfortunately, due to the Purple Badge rules your seats have been changed. </h2>" +
                            "you may choose new seats when you arrive to the theater. </h2>" +
                            "<br><h2 style=\"color:black;\">." +
                            "</bdo>");
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

    public void onReplyReceived2(MailResponse response) {
        if (response.getStatus() == ResponseStatus.Acknowledged) {
            System.out.println("Go check your E-Mail box for a new message..");
        }
        else
        {
            System.out.println("Failed to send you an email.");
        }
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
        else if (response.getStatus() == ResponseStatus.DeclinedMultConnections) {
            App.setUserPermission(-1);
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, null, "User is already logged in!"));
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

        if(permission == 4){
            try {
                App.setRoot("GeneralManagerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(permission == 3){
            try {
                App.setRoot("CinemaManagerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(permission >= 2) {
            try {
                App.setRoot("ContentManagerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(permission > 0){
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
