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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    private List<Ticket> tickets = null;
    private Link link = null;
    private int fromScreen =0;
    private double price=0;

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="payButton"
    private Button payButton; // Value injected by FXMLLoader

    @FXML // fx:id="orderDetailsVBox"
    private VBox orderDetailsVBox; // Value injected by FXMLLoader

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
        try {
            if (fromScreen==1) {//from ticket
                App.setRoot("PurchaseTicket.fxml"); //set the screen to the last page.
            }
           else if (fromScreen==2) { //from packege
                App.setRoot("BuyPackage.fxml"); //set the screen to the last page.
            }
            else if (fromScreen==3) { //from link
                App.setRoot("PurchaseLink.fxml"); //set the screen to the last page.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                alert.setContentText("Package was detected, would you like to pay with it?");
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
        if(fromScreen==1 && !tickets.isEmpty()){ // Details for Ticket
            price=tickets.get(0).getScreening().getMovie().getPrice()* tickets.size(); //set the price for the purchase

            /*set Order Details*/
            //set movie title in Order Details
            HBox hbMovie = new HBox();
            hbMovie.setSpacing(10);
            Label movieLabel = new Label("Movie: ");
            Text movieTitle = new Text(tickets.get(0).getScreening().getMovie().getEngName());
            movieLabel.setTextFill(Color.WHITE);
            movieTitle.setFill(Color.WHITE);
            hbMovie.getChildren().addAll(movieLabel ,movieTitle);

            //set Screening Details in Order Details
            HBox hbScreening = new HBox();
            hbMovie.setSpacing(10);
            Label screeningLabel = new Label("Screening: ");
            Text screeningText = new Text(tickets.get(0).getScreening().toString());
            screeningLabel.setTextFill(Color.WHITE);
            screeningText.setFill(Color.WHITE);
            hbScreening.getChildren().addAll(movieLabel ,movieTitle);

            orderDetailsVBox.getChildren().addAll(hbMovie, hbScreening);//add to screen

            //set ticket in Order Details
            for(Ticket ticket: tickets){
                HBox hbTicket = new HBox();
                hbMovie.setSpacing(10);
                Label ticketLabel = new Label("Screening: ");
                Text ticketSeat = new Text(ticket.getRow()+", "+ticket.getCol());
                screeningLabel.setTextFill(Color.WHITE);
                screeningText.setFill(Color.WHITE);
                hbTicket.getChildren().addAll(movieLabel ,movieTitle);
                orderDetailsVBox.getChildren().add(hbTicket);
            }

            //set price in Order Details
            HBox hbPrice = new HBox();
            hbMovie.setSpacing(10);
            Label PriceLabel = new Label("Price: ");
            Text PriceText = new Text(String.valueOf(price));
            PriceLabel.setTextFill(Color.WHITE);
            PriceText.setFill(Color.WHITE);
            hbPrice.getChildren().add(hbPrice);

            /*set price*/
            totalPrice = new Text(String.valueOf(price));
            totalPrice.setFill(Color.WHITE);
            totalPrice.setFont(Font.font(null, FontWeight.BOLD, 12));
        }

        else if( fromScreen==2 && !tickets.isEmpty()) { // Details for package
            // TODO: 23/06/2021 add price of a packege
            price= 500;

            /*set Order Details*/
            //set movie package
            HBox hbMovie = new HBox();
            hbMovie.setSpacing(10);
            Text moviePackageTitle = new Text("***Movie Package***");
            moviePackageTitle.setFill(Color.WHITE);
            hbMovie.getChildren().add(moviePackageTitle);

            //set price in Order Details
            HBox hbPrice = new HBox();
            hbMovie.setSpacing(10);
            Label PriceLabel = new Label("Price: ");
            Text PriceText = new Text(String.valueOf(price));
            PriceLabel.setTextFill(Color.WHITE);
            PriceText.setFill(Color.WHITE);
            hbPrice.getChildren().add(hbPrice);

            orderDetailsVBox.getChildren().addAll(hbMovie, hbPrice);//add to screen

            /*set price*/
            totalPrice = new Text(String.valueOf(price));
            totalPrice.setFill(Color.WHITE);
            totalPrice.setFont(Font.font(null, FontWeight.BOLD, 12));
        }

        else if(fromScreen==3 && link!=null) { // Details for Link
            price= link.getLinkPrice();

            /*set Order Details*/
            //set movie title in Order Details
            HBox hbMovie = new HBox();
            hbMovie.setSpacing(10);
            Label movieLabel = new Label("Movie: ");
            Text movieTitle = new Text(link.getMovie().getEngName());
            movieLabel.setTextFill(Color.WHITE);
            movieTitle.setFill(Color.WHITE);
            hbMovie.getChildren().addAll(movieLabel ,movieTitle);

            //set price in Order Details
            HBox hbPrice = new HBox();
            hbMovie.setSpacing(10);
            Label PriceLabel = new Label("Price: ");
            Text PriceText = new Text(String.valueOf(price));
            PriceLabel.setTextFill(Color.WHITE);
            PriceText.setFill(Color.WHITE);
            hbPrice.getChildren().add(hbPrice);

            //set availability
            SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd E HH:mm"); //set a date format
            GregorianCalendar availableTime = link.getDate();
            availableTime.set(Calendar.DAY_OF_WEEK_IN_MONTH,7);
            Text availableText = new Text(format.format(availableTime));
            availableText.setFill(Color.WHITE);

            orderDetailsVBox.getChildren().addAll(hbMovie, hbPrice, availableText);//add to screen

            /*set price*/
            totalPrice = new Text(String.valueOf(price));
            totalPrice.setFill(Color.WHITE);
            totalPrice.setFont(Font.font(null, FontWeight.BOLD, 12));
        }
    }
}
