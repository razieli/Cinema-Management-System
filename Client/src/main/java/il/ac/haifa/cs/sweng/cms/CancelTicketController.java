package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class CancelTicketController implements Initializable {

    //test DB
    private ArrayList<Ticket> ticketsList = new ArrayList<Ticket>();
    private  ArrayList<Screening> screeningList= new ArrayList<Screening>();
    private  ArrayList<Customer> customerList= new ArrayList<Customer>();
    private  ArrayList<Movie> movieList= new ArrayList<Movie>();
    private Theater theater1 = new Theater("place1",50,null);
    private Theater theater2 = new Theater("place2",80,null);
    private GregorianCalendar time1 = new GregorianCalendar(2021, 8, 20, 16, 16, 47);
    private GregorianCalendar time2 = new GregorianCalendar(2021, 6, 27, 8, 00, 00);

    private Movie movie1 = new Movie("movie1name","סרט1",2020,"cast1,cast2",90,10,"good movie", URI.create("www.imdb.com"), URI.create("www.google.com"));
    private Movie movie2 = new Movie("movie2name","סרט2",2021,"cast3,cast4",100,0,"better movie", URI.create("www.imdb2.com"), URI.create("www.google2.com"));

    private Screening screen1 = new Screening(movie1,theater1,time1);
    private Screening screen2 = new Screening(movie2,theater2,time2);

    private Customer cus1 = new Customer("Customer1", "Customer1","","");
    private Customer cus2 = new Customer("Customer2", "Customer2","","");


    private Ticket ticket1 = new Ticket(cus1,screen1,8);
    private Ticket ticket2 = new Ticket(cus2,screen2,42);

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.YY E HH:mm");

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
    public void handheldsCancelTicket(ActionEvent actionEvent) {
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
                ticketsList.remove(TicketsComboBox.getValue());
                alert.setHeaderText(null);
                alert.setContentText("Ticket Canceled");
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

            locationName.setText(TicketsComboBox.getValue().getScreening().getTheater().getPlaceName());
            theaterName.setText(String.valueOf(TicketsComboBox.getValue().getScreening().getTheater().getId()));

            String name = format.format(TicketsComboBox.getValue().getScreening().getDate().getTime()).toString();
            screeningTime.setText(name);
            seats.setText(String.valueOf(TicketsComboBox.getValue().getSeat()));
        }
    }


    /**
     * reset text fields
     */
    public void resetTexts(){
        movieName.setText("Movie Name");
        locationName.setText("Cinema Location");
        theaterName.setText("Theater");
        screeningTime.setText("dd.MM.YY E HH:mm");
        seats.setText("Seats");
        TicketsComboBox.setPromptText("Your Tickets");
    }


    /**
     * update scene components
     */
    public void updateScreen(){
        resetTexts();
        theater1.setId(0);
        theater2.setId(1);
        TicketsComboBox.setItems(FXCollections.observableArrayList(ticketsList));

    }

    /**
     * initializing scene components
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing TicketsCoboBox

        ticketsList.add(ticket1);
        ticketsList.add(ticket2);
        updateScreen();
    }



}
