package il.ac.haifa.cs.sweng.cms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class BuyPackageController {

    @FXML
    private Button backButton;

    @FXML
    private Button BuyPackage;

    @FXML
    public void handheldsBuyPackage(javafx.event.ActionEvent actionEvent) {
        // TODO: 23/06/2021 but package things.. 
    }

    
    @FXML
    public void handheldsBackButton(javafx.event.ActionEvent actionEvent) {
        try {
            App.setRoot("CustomerHome.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
