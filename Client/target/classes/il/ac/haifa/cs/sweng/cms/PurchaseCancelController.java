package il.ac.haifa.cs.sweng.cms;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PurchaseCancelController implements Initializable {

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="CancelTicket"
    private Button CancelTicket; // Value injected by FXMLLoader

    @FXML // fx:id="CancelLink"
    private Button CancelLink; // Value injected by FXMLLoader

    @FXML
    void handheldsCancelTicket(ActionEvent event) {
        try {
            App.setRoot("CancelTicket.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handheldsBackButton(ActionEvent actionEvent) {
        try {
            App.setRoot("CustomerHome.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handheldsCancelLink(ActionEvent actionEvent) {
        try {
            App.setRoot("CancelLink.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}