/**
 * Sample Skeleton for 'PurchaseTicket.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class PurchaseTicketController implements Initializable {
    private static Movie movie;
    private static Cinema pickedCinema = null;
    private static Screening pickedScreening = null;
    private static int pickSeats = 0;
//    private List<Cinema> cinemas= new ArrayList<Cinema>();
    private List<Ticket> seats =new ArrayList<Ticket>();
    private List<Cinema>cinemas= ViewMoviesController.getCinemas();
    private List<Ticket> tickets =new ArrayList<Ticket>();

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="accordion"
    private Accordion accordion; // Value injected by FXMLLoader

    @FXML // fx:id="selectScreeningPane"
    private TitledPane selectScreeningPane; // Value injected by FXMLLoader

    @FXML // fx:id="selectSeatPane"
    private TitledPane selectSeatPane; // Value injected by FXMLLoader

    @FXML // fx:id="selectOverviewPane"
    private TitledPane selectOverviewPane; // Value injected by FXMLLoader

    @FXML // fx:id="paymentButton"
    private Button paymentButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageTitle"
    private Label messageTitle; // Value injected by FXMLLoader

    @FXML // fx:id="screeningButtonBar"
    private ButtonBar screeningButtonBar; // Value injected by FXMLLoader

    @FXML // fx:id="addScreeningButton"
    private Button addScreeningButton; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaComboBox"
    private ComboBox<Cinema> cinemaComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="screeningComboBox"
    private ComboBox<Screening> screeningComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="seatComboBox"
    private ComboBox<Integer> seatComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="seatGridPane"
    private GridPane seatGridPane; // Value injected by FXMLLoader

    @FXML // fx:id="pickedTheater"
    private Text pickedTheater; // Value injected by FXMLLoader

    @FXML // fx:id="pickedHall"
    private Text pickedHall; // Value injected by FXMLLoader

    @FXML // fx:id="pickedTime"
    private Text pickedTime; // Value injected by FXMLLoader

    @FXML // fx: id="screeningChoiceBox"
    private ChoiceBox<Screening> screeningChoiceBox= new ChoiceBox();

    @FXML // fx:id="selectSeats"
    private Text selectSeats; // Value injected by FXMLLoader

    @FXML
    ImageView pic;

    public static Movie getMovie() {
        return movie;
    }

    public static void setMovie(Movie movie) {
        PurchaseTicketController.movie = movie;
    }

    public static Cinema getPickedCinema() {
        return pickedCinema;
    }

    public static void setPickedCinema(Cinema pickedCinema) {
        PurchaseTicketController.pickedCinema = pickedCinema;
    }


    /**
     * back button functionality
     */
    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("MovieOverview.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handheldsAddScreeningButton(ActionEvent event) {
        if(pickedCinema==null || pickedScreening==null) {
            //set a conformation alert
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(null);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("One or more sections is empty.");
            errorAlert.showAndWait();
        }

        else{
            //todo: set and load theater seats to seatGridPane.

            accordion.setExpandedPane(selectSeatPane);//open next section

            selectSeats.setText("How many seats for "+ pickedScreening.toString() + " show?" );//set hadar text

        }
    }

    @FXML
    void handheldsPayment(ActionEvent event) {
        if (tickets.isEmpty()){
            //set a error alert
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(null);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("You didn't pick any seats.");
            errorAlert.showAndWait();
        }

       else {
            // TODO: 23/06/2021 :find out about that static problam
//            PaymentController.setTickets(tickets);
//            PaymentController.setFromScreen(1);//set came from ticket
//            App.setRoot("Payment.fxml"); //set the sceen to the last page.
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accordion.setExpandedPane(selectScreeningPane);//open first pane

        messageTitle.setText("When and where would you like to watch \'"+movie.getEngName()+"\' ?");//setup header

        /*set Combobox options*/
        //Cinema
        cinemaComboBox.setItems(FXCollections.observableArrayList(cinemas));

        //if filter by cinema show pick by defult.
        if(pickedCinema != null) {
            cinemaComboBox.setValue(pickedCinema);

            //init theaterComboBox from picked cinema
            while (cinemas.isEmpty()) {
                Thread.yield();
            }
            List<Screening> screenList = new ArrayList<Screening>();
            for (Theater theater : pickedCinema.getTheaters()) {
                for (Screening screen : theater.getScreeningList())
                    screenList.add(screen);
            }

            screeningComboBox.setItems(FXCollections.observableArrayList(screenList));
        }

        //Seats
        for (int i=0 ; i<=4;i++){
            seatComboBox.getItems().add(i);
        }

        /*handlers on event*/
        //Cinema
        cinemaComboBox.setOnAction((event) -> {
            pickedCinema = cinemaComboBox.getValue();
            screeningComboBox.getItems().clear(); //clear choiceBox

            //init theaterComboBox from picked cinema
            if (pickedCinema!=null) {
                while (cinemas.isEmpty()) {
                    Thread.yield();
                }
                List<Screening> screenList = new ArrayList<Screening>();
                for (Theater theater : pickedCinema.getTheaters()) {
                    for (Screening screen : theater.getScreeningList())
                        screenList.add(screen);
                }

                screeningComboBox.setItems(FXCollections.observableArrayList(screenList));
            }
        });

        //Screening
        screeningComboBox.setOnAction((event) -> {
            if(!PurpleBadge.getInstance().getStatus()) {
                pickedScreening = screeningComboBox.getValue();
                System.out.println(pickedScreening);
                int[][] seatsMap = pickedScreening.getSeats();
                seatGridPane.getChildren().clear();
                for (int row = 0; row <= pickedScreening.getSeatsCapacity() / 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        try {
                            addSeat(pickedScreening, row, col);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        //seats
        seatComboBox.setOnAction(e->{
            if(!PurpleBadge.getInstance().getStatus()) {
                pickSeats = seatComboBox.getValue();
                tickets.clear();//clear all picked seats
                seatGridPane.getChildren().clear();//reload seats map
                for (int row = 0; row <= pickedScreening.getSeatsCapacity() / 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        try {
                            addSeat(pickedScreening, row, col);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            
            else{
                // TODO: 25/06/2021 add purches button
            }
        });



    }

    protected void addSeat(Screening screening, int row, int col) throws FileNotFoundException {
        AtomicBoolean seatFlag = new AtomicBoolean(false);
        Ticket ticket = new Ticket(null, screening, row,col);
        int[][] seatMap = screening.getSeats();
        ImageView imageView = new ImageView();
        if(seatMap[row][col] == 0){
            // TODO: 22/06/2021 toggle collor,   max seat to select
            imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/FreeSeat.png"),30,30,false,false));
        }

        else{
            imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/BusySeat.png"),30,30,false,false));
        }

        seatGridPane.add(imageView,col,row);
        seatGridPane.setVgap(5);
        seatGridPane.setHgap(5);
        // TODO: 22/06/2021  send taken seat to server
        imageView.setOnMouseClicked(e -> {
            if (seatMap[row][col] == 0) {
                seatFlag.set(!seatFlag.get());
                try {
                    if (seatFlag.get() == true) {
                        if (pickSeats == 0) {
                            //set a error alert
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle(null);
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("You can't pick more seats.");
                            errorAlert.showAndWait();
                            if (errorAlert.getResult() == ButtonType.OK) {
                                seatFlag.set(false);//if cant pick reset chack
                            }
                        } else {
                            imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/ChackedSeat.png"), 30, 30, false, false));
                            ticket.setCustomer((Customer)App.getUser());
                            tickets.add(ticket);
                            pickSeats--;
                        }
                    } else if (seatFlag.get() == false) {
                        imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/FreeSeat.png"), 30, 30, false, false));
                        ticket.setCustomer(null);
                        tickets.remove(ticket);
                        pickSeats++;
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }

        });
    }
}
