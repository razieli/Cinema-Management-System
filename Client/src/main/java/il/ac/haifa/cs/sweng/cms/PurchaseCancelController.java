package il.ac.haifa.cs.sweng.cms;


import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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