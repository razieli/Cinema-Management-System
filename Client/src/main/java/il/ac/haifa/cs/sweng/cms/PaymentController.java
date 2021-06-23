/**
 * Sample Skeleton for 'Payment.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    private List<Ticket> tickets = null;
    private Link link = null;
    private int fromScreen;


    @FXML // fx:id="titleText"
    private Text titleText; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="payButton"
    private Button payButton; // Value injected by FXMLLoader

    @FXML // fx:id="movieInfo"
    private Text movieInfo; // Value injected by FXMLLoader

    @FXML // fx:id="screeningInfo"
    private Text screeningInfo; // Value injected by FXMLLoader

    @FXML // fx:id="timeInfo"
    private Text timeInfo; // Value injected by FXMLLoader

    @FXML // fx:id="firstName"
    private TextField firstName; // Value injected by FXMLLoader

    @FXML // fx:id="lastName"
    private TextField lastName; // Value injected by FXMLLoader

    @FXML // fx:id="address"
    private TextField address; // Value injected by FXMLLoader

    @FXML // fx:id="city"
    private TextField city; // Value injected by FXMLLoader

    @FXML // fx:id="cardOwnerName"
    private TextField cardOwnerName; // Value injected by FXMLLoader

    @FXML // fx:id="cardOwnerLastName"
    private TextField cardOwnerLastName; // Value injected by FXMLLoader

    @FXML // fx:id="cardNumber"
    private TextField cardNumber; // Value injected by FXMLLoader

    @FXML // fx:id="cardExpiration"
    private DatePicker cardExpiration; // Value injected by FXMLLoader

    @FXML // fx:id="cvvNumber"
    private TextField cvvNumber; // Value injected by FXMLLoader

    @FXML // fx:id="totalPrice"
    private Text totalPrice; // Value injected by FXMLLoader

    @FXML
    void handheldsBackButton(ActionEvent event) {

    }

    @FXML
    void handheldsMovieDelete(MouseEvent event) {

    }

    @FXML
    void handheldsPay(ActionEvent event) {
        for(Ticket ticket : tickets) {
            if (ticket.getCustomer().isHas_package()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Pay With Package");
                alert.setHeaderText(null);
                alert.setContentText("a package was detected, would you like to pay with it?");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES)//delete operation from database
                {
                    App.getOcsfClient(this).updateTickets(ticket,true,true);
                } else {
                    App.getOcsfClient(this).updateTickets(ticket,true,false);
                }
            }
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public int getFromScreen() {
        return fromScreen;
    }

    public void setFromScreen(int fromScreen) {
        this.fromScreen = fromScreen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
