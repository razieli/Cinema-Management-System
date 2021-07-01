/**
 * Sample Skeleton for 'SuccessfulPurchase.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class SuccessfulPurchaseController implements Initializable {

    private static Payment payment=null;

    @FXML // fx:id="successfulVBox"
    private VBox successfulVBox; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseIDOverview"
    private Text PurchaseIDOverview; // Value injected by FXMLLoader

    @FXML // fx:id="TopMenu"
    private AnchorPane TopMenu; // Value injected by FXMLLoader


    @FXML
    private Text TheaterLabel;

    @FXML
    private Text MovieLabel;

    @FXML
    private Text NumLabel;

    @FXML
    private Text PlacesLabel;

    @FXML
    void handheldsBackToMovieList(ActionEvent event) {
        try {
            // storing the selected film to customise the newly created scene
            App.setRoot("CustomerHome.fxml");//load edit movie screen
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Payment getPayment() {
        return payment;
    }

    public static void setPayment(Payment payment) {
        SuccessfulPurchaseController.payment = payment;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(App.getStage().getUserData() instanceof List) { // TODO: link case
            List<Ticket> ticketList = (List<Ticket>) App.getStage().getUserData();
            Screening lastScreening = ticketList.get(ticketList.size()-1).getScreening();
            TheaterLabel.setText(TheaterLabel.getText() + " " + lastScreening.getTheater().getName());
            MovieLabel.setText(MovieLabel.getText() + " " + lastScreening.getMovie().getEngName());
            NumLabel.setText(NumLabel.getText() + " " + (ticketList.size()));
            ticketList.forEach(ticket -> PlacesLabel.setText(PlacesLabel.getText() + " " + "[Row: " + ticket.getRow() + " Seat: " + ticket.getCol() + "]"));
        }
        else if(App.getStage().getUserData() instanceof Link) {
            Link link = (Link) App.getStage().getUserData();
            TheaterLabel.setText("");
            MovieLabel.setText(MovieLabel.getText() + " " + link.getMovie().getEngName());
            NumLabel.setText("Activation: " + link.getDate().get(Calendar.DAY_OF_MONTH) + "." + (link.getDate().get(Calendar.MONTH) + 1) + "." + link.getDate().get(Calendar.YEAR) + " " + link.getDate().get(Calendar.HOUR_OF_DAY) + ":" + link.getDate().get(Calendar.MINUTE));
            PlacesLabel.setText("");
        } else {
            TheaterLabel.setText("");
            MovieLabel.setText("");
            NumLabel.setText("");
            PlacesLabel.setText("");
        }
    }
}

