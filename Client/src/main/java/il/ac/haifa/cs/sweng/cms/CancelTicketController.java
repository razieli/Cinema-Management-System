package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;




public class CancelTicketController implements Initializable {


    public List<Ticket> ticketList = new ArrayList<Ticket>();
    private List<Screening> screeningList= new ArrayList<Screening>();
    private List<Customer> customerList= new ArrayList<Customer>();
    private List<Movie> movieList= new ArrayList<Movie>();

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm");

    // javaFX buttons and fields
    @FXML
    private TextField movieName;

    @FXML
    private TextField locationName;

    @FXML
    private TextField theaterName;

    @FXML
    private TextField screeningTime;

    @FXML
    private TextField seats;

    @FXML
    private Button CancelTicket;

    @FXML // fx:id="Tickets"
    private ComboBox<Ticket> TicketsComboBox;


    /**
     * handling back button
     */
    @FXML
    public void handheldsBackButton(ActionEvent actionEvent) {
        try {
            App.setRoot("PurchaseCancel.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * handling cancel ticket button
     */
    @FXML
    public void handheldsCancelTicket(ActionEvent actionEvent) throws URISyntaxException {
        //set a warning alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        // if a ticket was selected remove it from DB
        if(TicketsComboBox.getValue()!= null) {
            alert.setTitle("Ticket Cancel");
            alert.setHeaderText(null);
            alert.setContentText("Are You Sure you want to cancel selected ticket?");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            //TODO: cancellation based on current time.
            if (alert.getResult() == ButtonType.YES)//delete operation from database
            {
                List <Ticket> tickets = new ArrayList<Ticket>();
                tickets.add(TicketsComboBox.getValue());
                App.getOcsfClient(this).updateTickets(tickets, false);
                ticketList.remove(TicketsComboBox.getValue());
                alert.setHeaderText(null);
                alert.setContentText("Ticket Canceled");
                updateScreen();
            } else {
                alert.setHeaderText(null);
                alert.setContentText("Ticket Did Not Canceled");
            }
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.OK);
            alert.showAndWait();
            updateScreen();
        }
        else{
            alert.setHeaderText(null);
            alert.setContentText("No Ticket Selected");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.OK);
            alert.showAndWait();
        }
    }


    /**
     * handle combobox selection
     */
    public void handheldsSelectTicket(ActionEvent actionEvent) {
        if (TicketsComboBox.getValue() != null) {
            //setting right screen based on TicketsCoboBox selection
            movieName.setText(TicketsComboBox.getValue().getScreening().getMovie().getEngName());
            theaterName.setText(String.valueOf(TicketsComboBox.getValue().getScreening().getTheater().getId()));
            screeningTime.setText(format.format(TicketsComboBox.getValue().getScreening().getDate().getTime()));
            seats.setText(String.valueOf(TicketsComboBox.getValue().getRow() + ", " + TicketsComboBox.getValue().getCol()));
//            locationName.setText(TicketsComboBox.getValue().getScreening().getTheater().getPlaceName()); // not working yet
        }
    }


    /**
     * reset text fields
     */
    public void resetTexts(){
        movieName.setText("Movie Name");
        locationName.setText("Cinema Location");
        theaterName.setText("Theater");
        screeningTime.setText("dd.MM.YYYY E HH:mm");
        seats.setText("Seats");
        TicketsComboBox.setPromptText("Your Tickets");
    }


    /**
     * update scene components
     */
    public void updateScreen(){
        resetTexts();

        TicketsComboBox.setItems(FXCollections.observableArrayList(ticketList));

    }

    /**
     * initializing scene components
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing TicketsCoboBox
        try{
            Customer customer = (Customer) App.getUser();
            App.getOcsfClient(this).getListOfTickets();
            while(ticketList.isEmpty()) { Thread.yield(); }
            List <Ticket> ticks = new ArrayList<Ticket>();
            for (Ticket ticket : ticketList) {
                if(ticket.getCustomer().getUserName().equals(customer.getUserName())){
                    ticks.add(ticket);
                }
            }
            ticketList = ticks;
            updateScreen();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setTickets(List<Ticket> tickets) {
        this.ticketList = tickets;
    }
}
