package il.ac.haifa.cs.sweng.cms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuyPackageController implements Initializable  {

    @FXML
    private Button backButton;

    @FXML
    private Button BuyPackage;

    @FXML
    public void handheldsBuyPackage(javafx.event.ActionEvent actionEvent) {
        // TODO: 23/06/2021 but package things..
//        PaymentController.setLink(link);
//        PaymentController.setFromScreen(2); //came from buy packege
//        App.setRoot("Payment.fxml"); //set the sceen to the last page.
    }

    
    @FXML
    public void handheldsBackButton(javafx.event.ActionEvent actionEvent) {
        try {
            App.setRoot("CustomerHome.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


