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
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PurchaseTicketController implements Initializable {
    private static Movie movie;
    private static Cinema pickedCinema = null;
    private static Screening screening = null;
    private static int pickSeats = 0;
    private List<Cinema> cinemas= new ArrayList<Cinema>();
    private List<Ticket> seats =new ArrayList<Ticket>();
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
    private Text messageTitle; // Value injected by FXMLLoader

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

    @FXML // fx:id="seatStackPane"
    private StackPane seatStackPane; // Value injected by FXMLLoader

    @FXML // fx:id="seatsAncour"
    private Pane seatsPane; // Value injected by FXMLLoader

    @FXML // fx:id="PBAcceptVBox"
    private VBox PBAcceptVBox; // Value injected by FXMLLoader

    @FXML // fx:id="PBAcceptButton"
    private Button PBAcceptButton; // Value injected by FXMLLoader

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
//            screeningComboBox.setValue(null);
//            seatComboBox.setValue(null);
//            cinemaComboBox.setValue(null);
            tickets.clear();
            App.setRoot("MovieOverview.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handheldsAddScreeningButton(ActionEvent event) {
//        if(pickedCinema==null || screening==null) {
//            //set a conformation alert
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//            errorAlert.setTitle(null);
//            errorAlert.setHeaderText(null);
//            errorAlert.setContentText("One or more sections is empty.");
//            errorAlert.showAndWait();
//        }
//
//        else{
//            //todo: set and load theater seats to seatGridPane.
//
//            accordion.setExpandedPane(selectSeatPane);//open next section
//
//            selectSeats.setText("How many seats for "+ screening.toString() + " show?" );//set hadar text
//
//        }
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
        accordion.setExpandedPane(selectSeatPane);//open next section
        /*
         *set components size to adapt window size
         */
        seatStackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            PBAcceptVBox.prefWidthProperty().bind(seatStackPane.widthProperty());
        });

        seatStackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            PBAcceptVBox.prefHeightProperty().bind(seatStackPane.heightProperty());
        });
        seatStackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            seatsPane.prefWidthProperty().bind(seatStackPane.widthProperty());
        });

        seatStackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            seatsPane.prefHeightProperty().bind(seatStackPane.heightProperty());
        });
//        accordion.setExpandedPane(selectScreeningPane);//open first pane
//
//        messageTitle.setText("When and where would you like to watch \'"+movie.getEngName()+"\' ?");//setup header
//
//        /*set Combobox options*/
//        //Cinema
//        for(Screening s: movie.getScreening()){
//            if (cinemas.isEmpty() || !cinemas.contains(s.getTheater().getCinema())){
//                cinemas.add(s.getTheater().getCinema());
//            }
//        }
//        if (!cinemas.isEmpty())
//            cinemaComboBox.setItems(FXCollections.observableArrayList(cinemas));
//
//        //Screening
//        //if filter by cinema show pick by defult.
//        if(pickedCinema != null) {
//            cinemaComboBox.setValue(pickedCinema);
//
//            //init theaterComboBox from picked cinema
//            while (cinemas.isEmpty()) {
//                Thread.yield();
//            }
//            List<Screening> screenList = new ArrayList<Screening>();
//            for(Screening s: movie.getScreening()){
//                if(s.getTheater().getCinema().equals(pickedCinema)){
//                    screenList.add(s);
//                }
//            }
//            screeningComboBox.setItems(FXCollections.observableArrayList(screenList));
//        }

        //Seats
        for (int i=0 ; i<=4;i++){
            seatComboBox.getItems().add(i);
        }

//        /*handlers on event*/
//        //Cinema
//        cinemaComboBox.setOnAction((event) -> {
//            pickedCinema = cinemaComboBox.getValue();
//            screeningComboBox.getItems().clear(); //clear choiceBox
//
//            //init theaterComboBox from picked cinema
//            if (pickedCinema!=null) {
//                while (cinemas.isEmpty()) {
//                    Thread.yield();
//                }
//                List<Screening> screenList = new ArrayList<Screening>();
//                for (Theater theater : pickedCinema.getTheaters()) {
//                    for (Screening screen : theater.getScreeningList())
//                        screenList.add(screen);
//                }
//
//                screeningComboBox.setItems(FXCollections.observableArrayList(screenList));
//            }
//        });
//
//        //Screening
//        screeningComboBox.setOnAction((event) -> {
//            screening = screeningComboBox.getValue();
//            System.out.println(screening);

            if(!PurpleBadge.getInstance().isPurpleBadge(screening.getDate()) ) { //if purpleBadge is on in the same values
                seatStackPane.getChildren().remove(seatsPane);
                seatStackPane.getChildren().add(seatsPane);

                int[][] seatsMap = screening.getSeats();
                seatGridPane.getChildren().clear();
                for (int row = 0; row <= screening.getSeatsCapacity() / 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        try {
                            addSeat(screening, row, col);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            else{ //purple badge case
                seatStackPane.getChildren().remove(PBAcceptVBox);
                seatStackPane.getChildren().add(PBAcceptVBox);

                AtomicBoolean flag = new AtomicBoolean(true);
                PBAcceptButton.setOnAction(e->{
                    flag.set(!flag.get());
                    if(flag.get()==false) {

                    System.out.format("%b , present: %d, ecpected: %d\n",screening.getTickets().size()+pickSeats > screening.getRealSeatsCapacity(),screening.getTickets().size()+pickSeats , screening.getRealSeatsCapacity());
                    if (pickSeats==0 || (screening.getTickets().size()+pickSeats) > screening.getRealSeatsCapacity()) {//alert for a case of out of bounds
                        //set a error alert
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle(null);
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Pleas pick legal number of seats first.");
                        errorAlert.showAndWait();
                        flag.set(false);
                    }
                    else{
                        PBAcceptButton.setText("Cancel");
                        int[][] seatMap = screening.getSeats();
                        Ticket lastTicket;

                        if(screening.getTickets().isEmpty()) {
                            int i = 1;
                            int j = 1;
                            for (int k = 0; k < pickSeats; k++) {
                                tickets.add(new Ticket((Customer) App.getUser(), screening, i, j));//add Tickets to purchase
                                j++;
                                j = j % 10;
                                if (i == 0) {
                                    j = 1;
                                    i += 2;
                                }
                            }
                            System.out.println(tickets);
                        }

                        else { // TODO: 27/06/2021 what hapand if not empthy???????
                            lastTicket = screening.getTickets().get(screening.getTickets().size());
                            if (lastTicket.getCustomer().equals((Customer) App.getUser())) {//if the same customer

                                int i = lastTicket.getRow() + 1;
                                int j = lastTicket.getCol() + 1;
                                for (int k = 0; k < pickSeats; k++) {
                                    j++;
                                    j = j % 10;
                                    if (i == 0) {
                                        j = 1;
                                        i += 2;
                                    }
                                    tickets.add(new Ticket((Customer) App.getUser(), screening, i, j));//add Tickets to purchase
                                    System.out.println(tickets.size());
                                }
                            } else {//if different than the last customer
                                int i = lastTicket.getRow() + 1;
                                int j = lastTicket.getCol() + 1;
                                j++;
                                j = j % 10;
                                if (i == 0) {
                                    j = 1;
                                    i += 2;
                                }
                                screening.addTicket(new Ticket(null, null, i, j));//add blank seat

                                for (int k = 0; k < pickSeats; k++) {
                                    j++;
                                    j = j % 10;
                                    if (i == 0) {
                                        j = 1;
                                        i += 2;
                                    }
                                    tickets.add(new Ticket((Customer) App.getUser(), screening, i, j));//add Tickets to purchase
                                }
                                System.out.println(tickets);
                            }
                        }
                    }
                    }
                    else {
                        PBAcceptButton.setText("Accept");//change button text

                        tickets.clear(); //Remove all tickets that insert
                        if(!screening.getTickets().isEmpty() && screening.getTickets().get(screening.getTickets().size()).getCustomer()==null){//if added blank seat remove it.
                            screening.getTickets().remove(screening.getTickets().size());
                        }
                    }});
            }
            System.out.println(tickets.size());
//        });

        //seats
        seatComboBox.setOnAction(e->{
            pickSeats = seatComboBox.getValue();
            if(!PurpleBadge.getInstance().isPurpleBadge(screening.getDate())) {
                tickets.clear();//clear all picked seats
                seatGridPane.getChildren().clear();//reload seats map
                for (int row = 0; row <= screening.getSeatsCapacity() / 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        try {
                            addSeat(screening, row, col);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            
            else{
                if((screening.getTickets().size()+pickSeats) > screening.getRealSeatsCapacity()){ //case of reach limit of purple badge
                    //set a error alert
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle(null);
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("You can't pick more than "+ (screening.getRealSeatsCapacity()-screening.getTickets().size()) +" seats.");
                    errorAlert.showAndWait();
                }
                // TODO: 25/06/2021 add purches button
            }
        });
    }

    protected void addSeat(Screening s, int row, int col) throws FileNotFoundException {
        AtomicBoolean seatFlag = new AtomicBoolean(false);
        Ticket ticket = new Ticket(null, s, row,col);
        int[][] seatMap = s.getSeats();
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
                    if (seatFlag.get() == true) {//if purpleBadge is on in the same values
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
    /**
     * get movie object
     * @return movie
     */
    public static Screening getScreening() {
        return screening;
    }

    /**
     * set movie object
     * @param screening
     */
    public static void setScreening(Screening screening) {
        PurchaseTicketController.screening = screening;
    }
}
