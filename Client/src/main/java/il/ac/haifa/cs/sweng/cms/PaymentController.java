/**
 * Sample Skeleton for 'Payment.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.UpdateTicketsResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentController implements Initializable {
    private static Link link = null;
    private static int fromScreen =0;
    private double price=0;
    private static Movie movie;
    private static Screening screening = null;
    private static int pickSeats = 0;
    private List<Ticket> tickets =new ArrayList<Ticket>(),newTickets =new ArrayList<Ticket>() ;
    private String inputFirstName, inputLastName,inputEmail, inputPhone, inputCardOwnerName, inputCardOwnerLastName, inputCardNumber, inputCvvNumber;
    private int inputCardExpirationYear=0,inputCardExpirationMonth=0;
    private GregorianCalendar inputExpirationDate;
    private Boolean messageStatus=false;
    private ImageView seatBlockAttemptImage;
    private Ticket ticket;


    @FXML // fx:id="accordion"
    private Accordion accordion; // Value injected by FXMLLoader

    @FXML // fx:id="selectPane"
    private TitledPane selectPane; // Value injected by FXMLLoader

    @FXML // fx:id="selectStackPane"
    private StackPane selectStackPane; // Value injected by FXMLLoader

    @FXML // fx:id="seatsBorderPane"
    private BorderPane seatsBorderPane; // Value injected by FXMLLoader

    @FXML // fx:id="selectSeats"
    private Text selectSeats; // Value injected by FXMLLoader

    @FXML // fx:id="selectSeatsButton"
    private Button selectSeatsButton; // Value injected by FXMLLoader

    @FXML // fx:id="seatComboBox"
    private ComboBox<Integer> seatComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="seatStackPane"
    private StackPane seatStackPane; // Value injected by FXMLLoader

    @FXML // fx:id="PBAcceptVBox"
    private VBox PBAcceptVBox; // Value injected by FXMLLoader

    @FXML // fx:id="PBAcceptButton"
    private Button PBAcceptButton; // Value injected by FXMLLoader

    @FXML // fx:id="seatsPane"
    private Pane seatsPane; // Value injected by FXMLLoader

    @FXML // fx:id="seatGridPane"
    private GridPane seatGridPane; // Value injected by FXMLLoader

    @FXML // fx:id="packegeVBox"
    private VBox packegeVBox; // Value injected by FXMLLoader

    @FXML // fx:id="BuyPackageButton"
    private Button BuyPackageButton; // Value injected by FXMLLoader

    @FXML // fx:id="paymentPane"
    private TitledPane paymentPane; // Value injected by FXMLLoader

    @FXML // fx:id="payScrollPane"
    private ScrollPane payScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="payBorderPane"
    private BorderPane payBorderPane; // Value injected by FXMLLoader

    @FXML // fx:id="orderDetailsVBox"
    private VBox orderDetailsVBox; // Value injected by FXMLLoader

    @FXML // fx:id="firstName"
    private TextField firstName; // Value injected by FXMLLoader

    @FXML // fx:id="lastName"
    private TextField lastName; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="phone"
    private TextField phone; // Value injected by FXMLLoader

    @FXML // fx:id="cardOwnerName"
    private TextField cardOwnerName; // Value injected by FXMLLoader

    @FXML // fx:id="cardOwnerLastName"
    private TextField cardOwnerLastName; // Value injected by FXMLLoader

    @FXML // fx:id="cardNumber"
    private TextField cardNumber; // Value injected by FXMLLoader

    @FXML // fx:id="cvvNumber"
    private TextField cvvNumber; // Value injected by FXMLLoader

    @FXML // fx:id="totalPrice"
    private Text totalPrice; // Value injected by FXMLLoader

    @FXML // fx:id="cardExpirationYear"
    private ComboBox<Integer> cardExpirationYear; // Value injected by FXMLLoader

    @FXML // fx:id="cardExpirationMonth"
    private ComboBox<Integer> cardExpirationMonth; // Value injected by FXMLLoader

    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            if (fromScreen==1 || fromScreen==2) {//from ticket or link
                tickets.clear();
                if (!selectPane.isVisible())
                    selectPane.setVisible(true);
                if (seatComboBox.getValue()!=null)
                    seatComboBox.setValue(0);
                if (fromScreen==2)//link
                    paymentPane.setText("Payment (Step 2/2)");
                App.setRoot("MovieOverview.fxml"); //set the screen to the last page.
            }
           else if (fromScreen==3) { //from packege
                App.setRoot("CustomerHome.fxml"); //set the screen to the last page.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void handheldsPay(ActionEvent event) {
        /*update fields*/
        inputFirstName=firstName.getText();
        inputLastName=lastName.getText();
        inputEmail=email.getText();
        inputPhone=phone.getText();
        inputCardOwnerName=cardOwnerName.getText();
        inputCardOwnerLastName=cardOwnerLastName.getText();
        inputCardNumber= cardNumber.getText();
        inputCvvNumber= cvvNumber.getText();

        if (isChecked()){
            if(fromScreen==1 && !tickets.isEmpty()) {
                    if (tickets.get(0).getCustomer().isHas_package()) {
                        boolean payWithPackage;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Pay With Package");
                        alert.setHeaderText(null);
                        alert.setContentText("Package was detected, would you like to pay with it?");
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();
                        payWithPackage = alert.getResult() == ButtonType.YES;

                       if(payWithPackage==true && tickets.get(0).getCustomer().getPackageTicketsRemaining()>pickSeats) {


                           try {
                               App.getOcsfClient(this).updateTickets(tickets, true, payWithPackage);
                               while (newTickets.isEmpty()) { Thread.yield(); }
                               for (Ticket tic : newTickets)
                                   System.out.println(tic.getId());

                            if(messageStatus){
                               // TODO: 30/06/2021 update the packeg statuse
                               sendMail(newTickets, newTickets.get(0).getCustomer().getPackageTicketsRemaining());//send mail
                               App.setRoot("SuccessfulPurchase.fxml"); //set the screen to the last page.
                            }
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                       else{
                           Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                           errorAlert.setTitle(null);
                           errorAlert.setHeaderText(null);
                           errorAlert.setContentText("You don't have enough tickets remaining in your package.");
                           errorAlert.showAndWait();
                       }
                    } else { //in case of no package available for the customer
                        try {
                            Payment payment = new Payment(inputCardOwnerName, inputCardOwnerLastName, (GregorianCalendar) GregorianCalendar.getInstance(), inputEmail, inputPhone, inputCardNumber, inputExpirationDate, inputCvvNumber);
                            for (Ticket tic : tickets)
                                tic.setPayment(payment);

//                            System.out.println(tickets);
                            App.getOcsfClient(this).updateTickets(tickets, true, false);
                            // TODO: Declare success only after acknowledge from server was received.

                            while (newTickets.isEmpty()) { Thread.yield(); }
                            for (Ticket tic : newTickets)
                                System.out.println(tic.getId());

                            if(messageStatus){
                                sendMail(newTickets);//send mail
                                App.setRoot("SuccessfulPurchase.fxml"); //set the screen to the last page.
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

            } else if (fromScreen==2 && link!=null) {//links
                try {
                    // TODO: set the selected movie.
                    Payment payment = new Payment(inputCardOwnerName, inputCardOwnerLastName, (GregorianCalendar) GregorianCalendar.getInstance(), inputEmail, inputPhone, inputCardNumber, inputExpirationDate, inputCvvNumber);
                    link = new Link((Customer) App.getUser(), (GregorianCalendar) GregorianCalendar.getInstance(), movie);
                    link.setPayment(payment);

                    System.out.println(link);
                    App.getOcsfClient(this).updateLinks(link, true);
                    // TODO: Declare success only after acknowledge from server was received.
                    System.out.println(messageStatus);
                    if(messageStatus){
                        sendMail(link);//send mail
                        App.setRoot("SuccessfulPurchase.fxml"); //set the screen to the last page.
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if(fromScreen==3){//package
                Customer customer=(Customer)App.getUser();
                if(customer.isHas_package()) {
                    Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                    errorAlert.setTitle(null);
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("You already have a ticket package.");
                    errorAlert.showAndWait();
                }
                else{
                    try {
                        Payment payment = new Payment(inputCardOwnerName, inputCardOwnerLastName, (GregorianCalendar) GregorianCalendar.getInstance(), inputEmail, inputPhone, inputCardNumber, inputExpirationDate, inputCvvNumber);
                        customer.setPayment(payment);
                        customer.addPackage();
                        App.getOcsfClient(this).updateCustomer(customer);

                        System.out.println(messageStatus);
                        if(messageStatus){
    //                        sendMail(link);//send mail
                            App.setRoot("SuccessfulPurchase.fxml"); //set the screen to the last page.
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else{//if one or more of the section are empty
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(null);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("One or more sections are empty/invalid.");
            errorAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        payScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            payBorderPane.prefWidthProperty().bind(payScrollPane.widthProperty());
        });

        accordion.setExpandedPane(selectPane);//open next section

        /*
         *set components size to adapt window size
         */
        if(fromScreen == 1) { //from overview
            selectStackPane.getChildren().remove(seatsBorderPane);
            selectStackPane.getChildren().add(seatsBorderPane);

            if (PurpleBadge.getInstance().isPurpleBadge(screening.getDate())) {//in purpleBadge case
                seatStackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                    PBAcceptVBox.prefWidthProperty().bind(seatStackPane.widthProperty());
                });

                seatStackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                    PBAcceptVBox.prefHeightProperty().bind(seatStackPane.heightProperty());
                });
            } else { //in regular case
                seatStackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                    seatsPane.prefWidthProperty().bind(seatStackPane.widthProperty());
                });

                seatStackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                    seatsPane.prefHeightProperty().bind(seatStackPane.heightProperty());
                });
            }

            /*
            * set selected seat
            */
            //Seats
            for (int i=0 ; i<=4;i++){
                seatComboBox.getItems().add(i);
            }
            seatComboBox.setValue(0);

            if(!PurpleBadge.getInstance().isPurpleBadge(screening.getDate()) ) { //if purpleBadge is on in the same values
                seatStackPane.getChildren().remove(seatsPane);
                seatStackPane.getChildren().add(seatsPane);

                int[][] seatsMap = screening.getSeats();
                seatGridPane.getChildren().clear();
                for (int row = 0; row <= screening.getSeatsCapacity() / 10; row++) {
                    for (int col = 0; col < 10; col++) {
//                        System.out.println("seats: "+seatsMap[row][col]);
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

                        System.out.format("%b , present: %d, expected: %d\n",screening.getTickets().size()+pickSeats > screening.getRealSeatsCapacity(),screening.getTickets().size()+pickSeats , screening.getRealSeatsCapacity());
                        if (pickSeats==0 || (screening.getTickets().size()+pickSeats) > screening.getRealSeatsCapacity()) {//alert for a case of out of bounds
                            //set a error alert
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle(null);
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("Please pick legal number of seats first.");
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
                                    if (j == 0) {
//                                        j = 1;
                                        i += 1;
                                    }
                                }

                            }

                            else { // TODO: 27/06/2021 what hapand if not empthy???????
                                lastTicket = screening.getTickets().get(screening.getTickets().size()-1);
                                if (lastTicket.getCustomer().equals((Customer) App.getUser())) {//if the same customer

                                    int i = lastTicket.getRow() + 1;
                                    int j = lastTicket.getCol() + 1;
                                    for (int k = 0; k < pickSeats; k++) {
                                        j++;
                                        j = j % 10;
                                        if (j == 0) {
//                                            j = 1;
                                            i += 1;
                                        }
                                        tickets.add(new Ticket((Customer) App.getUser(), screening, i, j));//add Tickets to purchase
                                        System.out.println(tickets.size());
                                    }
                                } else {//if different than the last customer
                                    int i = lastTicket.getRow() + 1;
                                    int j = lastTicket.getCol() + 1;
                                    j++;
                                    j = j % 10;
                                    if (j == 0) {
//                                        j = 1;
                                        i += 1;
                                    }
                                    screening.addTicket(new Ticket(null, null, i, j));//add blank seat

                                    for (int k = 0; k < pickSeats; k++) {
                                        j++;
                                        j = j % 10;
                                        if (j == 0) {
//                                            j = 1;
                                            i += 1;
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
                        System.out.println(tickets.size());
                        if(!screening.getTickets().isEmpty() && screening.getTickets().get(screening.getTickets().size()-1).getCustomer()==null){//if added blank seat remove it.
                            screening.getTickets().remove(screening.getTickets().size()-1);
                        }
                    }});
            }
            System.out.println(tickets.size());


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
                }
            });


            /*
            * set payment Details
            */
            selectSeatsButton.setOnAction(e->{
                if(!tickets.isEmpty()){ // Details for Ticket
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
                    hbScreening.getChildren().addAll(screeningLabel ,screeningText);

                    orderDetailsVBox.getChildren().addAll(hbMovie, hbScreening);//add to screen

                    //set ticket in Order Details
                    HBox hbTicket = new HBox();
                    hbMovie.setSpacing(10);
                    Label ticketLabel = new Label("Seats: ");
                    ticketLabel.setTextFill(Color.WHITE);
                    hbTicket.getChildren().add(ticketLabel);
                    for(Ticket ticket: tickets){
                        Text ticketSeat = new Text("(" +ticket.getRow()+", "+ticket.getCol()+") ");
                        ticketSeat.setFill(Color.WHITE);
                        hbTicket.getChildren().add(ticketSeat);
                    }
                    orderDetailsVBox.getChildren().add(hbTicket);

                    //set price in Order Details
                    HBox hbPrice = new HBox();
                    hbMovie.setSpacing(10);
                    Label PriceLabel = new Label("Price: ");
                    Text PriceText = new Text(String.valueOf(price));
                    PriceLabel.setTextFill(Color.WHITE);
                    PriceText.setFill(Color.WHITE);
                    hbPrice.getChildren().addAll(PriceLabel ,PriceText);
                    orderDetailsVBox.getChildren().add(hbPrice);

                    /*set price*/
                    totalPrice.setText(String.valueOf(price));

                    accordion.setExpandedPane(paymentPane);//open payment section


                }

                else if(!PurpleBadge.getInstance().getStatus()){
                    // TODO: 29/06/2021 error.
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle(null);
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("You didn't pick any seat.");
                    errorAlert.showAndWait();
                }
            });

        }

        else if(fromScreen==2) {//from link
            selectPane.setVisible(false);
            paymentPane.setText("Payment");
            accordion.setExpandedPane(paymentPane);//open next section

            /*
             * set payment Details
             */
            if(link!=null) { // Details for Link
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
                hbPrice.getChildren().addAll(PriceLabel,PriceText);

                //set availability
                HBox hbAvailable = new HBox();
                Label availableLabel = new Label("Available until: ");
                GregorianCalendar availableTime = new GregorianCalendar(link.getDate().get(Calendar.YEAR),link.getDate().get(Calendar.MONTH),link.getDate().get(Calendar.DAY_OF_MONTH),link.getDate().get(Calendar.HOUR_OF_DAY),link.getDate().get(Calendar.MINUTE));
                availableTime.add(Calendar.DAY_OF_MONTH,7);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm"); //set a date format
                Text availableText = new Text(format.format(availableTime.getTime()));
                availableLabel.setTextFill(Color.WHITE);
                availableText.setFill(Color.WHITE);
                hbAvailable.getChildren().addAll(availableLabel,availableText);

                orderDetailsVBox.getChildren().addAll(hbMovie, hbPrice, hbAvailable);//add to screen

                /*set price*/
                totalPrice.setText(String.valueOf(price));
            }
            
            else{
                // TODO: 29/06/2021 error
            }

        }

        else if (fromScreen==3) {//from packege
            selectStackPane.getChildren().remove(packegeVBox);
            selectStackPane.getChildren().add(packegeVBox);

            BuyPackageButton.setOnAction(e->{//
                accordion.setExpandedPane(paymentPane);//open next section
            });


            /*
             * set payment Details
             */
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
            hbPrice.getChildren().addAll(PriceLabel,PriceText);

            orderDetailsVBox.getChildren().addAll(hbMovie, hbPrice);//add to screen

            /*set price*/
            totalPrice.setText(String.valueOf(price));

            }


        /*
         *get payment details
         */
        for (int i = GregorianCalendar.getInstance().get(Calendar.YEAR); i <GregorianCalendar.getInstance().get(Calendar.YEAR)+10 ; i++) {
            cardExpirationYear.getItems().add(i);
        }

        cardExpirationYear.setOnAction(e1->{
            inputCardExpirationYear=cardExpirationYear.getValue();
            cardExpirationMonth.getItems().clear();

            if(inputCardExpirationYear==GregorianCalendar.getInstance().get(Calendar.YEAR)){
                for (int i = GregorianCalendar.getInstance().get(Calendar.MONTH); i <= 12; i++) {
                    cardExpirationMonth.getItems().add(i);
                }
            }
            else {
                for (int i = 1; i <= 12; i++) {
                    cardExpirationMonth.getItems().add(i);
                }
            }

            cardExpirationMonth.setOnAction(e2->{
                inputCardExpirationMonth=cardExpirationMonth.getValue();
                inputExpirationDate= new GregorianCalendar(inputCardExpirationYear, inputCardExpirationMonth ,1);//set for month later
            });

        });
    }


    /**
     * add seats to grid and set there stats
     * @param s
     * @param row
     * @param col
     * @throws FileNotFoundException
     */
    protected void addSeat(Screening s, int row, int col) throws FileNotFoundException {
        AtomicBoolean seatFlag = new AtomicBoolean(false);
        Ticket ticket = new Ticket(null, s, row,col);
        int[][] seatMap = s.getSeats();
        ImageView imageView = new ImageView();

        if(seatMap[row][col] == 0){
            // TODO: 22/06/2021 toggle collor,   max seat to select
            imageView.setImage(new Image("FreeSeat.png", 30,30,false,false));
        }

        else{
            imageView.setImage(new Image("BusySeat.png", 30,30,false,false));
        }

        seatGridPane.add(imageView,col,row);
        seatGridPane.setVgap(5);
        seatGridPane.setHgap(5);
        // TODO: 22/06/2021  send taken seat to server
        imageView.setOnMouseClicked(e -> {
            if (seatMap[row][col] == 0) {
                seatFlag.set(!seatFlag.get());
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
                        imageView.setImage(new Image("ChackedSeat.png", 30,30,false,false));
                        ticket.setCustomer((Customer)App.getUser());
                        tickets.add(ticket);
                        // TODO: 30/06/2021  mark seat as taken in screening seat[][]<- -1
                        pickSeats--;
                        seatBlockAttemptImage = imageView;
                        this.ticket = ticket;
                        App.getOcsfClient(this).blockSeat(screening, row, col);
                    }
                } else if (seatFlag.get() == false) {
                    imageView.setImage(new Image("FreeSeat.png", 30,30,false,false));
                    ticket.setCustomer(null);
                    tickets.remove(ticket);
                    // TODO: 30/06/2021  mark seat as not taken in screening seat[][] <-0
                    pickSeats++;
                }
            }

        });
    }


    /*
    *getters and setters
    */
    public static Movie getMovie() {
        return movie;
    }

    public static void setMovie(Movie movie) {
        PaymentController.movie = movie;
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
        PaymentController.screening = screening;
    }

    public int getFromScreen(){
        return fromScreen;
    }

    public static void setFromScreen(int fromScreen){
        PaymentController.fromScreen=fromScreen;
    }

    public Link getLink() {
        return link;
    }

    public static void setLink(Link link) {
        PaymentController.link = link;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getNewTickets() {
        return newTickets;
    }

    public void setNewTickets(List<Ticket> newTickets) {
        this.newTickets = newTickets;
    }

    public Boolean getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Boolean messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * chack if valid details inserts
     * @return
     */
    private Boolean isChecked(){
       if( inputFirstName!=null || inputLastName!=null ||  inputPhone!=null|| inputCardOwnerName!=null || inputCardOwnerLastName!=null || inputCardNumber!=null || inputCardExpirationYear!=0||inputCardExpirationMonth!=0|| inputCvvNumber!=null || inputEmail!=null){
//           System.out.println("email: "+inputEmail+" is "+isValidMail(inputEmail));
//           System.out.println("phone: "+inputPhone+" is "+isValidPhone(inputPhone));
//           System.out.println("CVV: "+ inputCvvNumber+" is " +isValidCVVNumber(inputCvvNumber) );
//           System.out.println("credit card: "+inputCardNumber+" is "+isValidCreditCard(Long.parseLong(inputCardNumber)));
           if(isValidMail(inputEmail) && isValidPhone(inputPhone) && isValidCreditCard(Long.parseLong(inputCardNumber))&& isValidCVVNumber(inputCvvNumber) &&(fromScreen==1 && !tickets.isEmpty()) || (fromScreen==2 && link!=null)||fromScreen==3)
               return true;
       }
       return false;
    }

    /**
     * chack if valid email address
     * @param email
     * @return
     */
    public static boolean isValidMail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     * chack if valid phone number
     * @param phone
     * @return
     */
    public static boolean isValidPhone(String phone)
    {

        /*
         *The given argument to compile() method is regular expression. With the help of regular expression we can validate mobile number.
         * 1) Begins with 0 or 972
         * 2) Then contains 5.
         * 3) Then contains 8 digits
         */
        Pattern p = Pattern.compile("(0|972)?[5][0-9]{8}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }


    /**
     * chack if valid credit card
     * @param number
     * @return
     */
    // Return true if the card number is valid
    public static boolean isValidCreditCard(long number)
    {
        //4 for Visa cards
        //5 for Master cards
        //37 for American Express cards
        //6 for Discover cards
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    // Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    public static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    // Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }

    /**
     * chack if valid CVV number
     * @param ccvNum
     * @return
     */
    public static boolean isValidCVVNumber(String ccvNum)
    {
        // Regex to check valid CVV number.
        String regex = "^[0-9]{3,4}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (ccvNum == null)
        {
            return false;
        }

        // Find match between given string
        // and regular expression
        // uaing Pattern.matcher()

        Matcher m = p.matcher(ccvNum);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    private void sendMail(List<Ticket> tickets){
        String color = "blue";
        String firstNameOfCustomer = tickets.get(0).getPayment().getFirstName();
        String lastNameOfCustomer = tickets.get(0).getPayment().getLastName();
        String nameOfCinema = tickets.get(0).getScreening().getTheater().getCinema().getName();
        String nameOfTheater = tickets.get(0).getScreening().getTheater().getPlaceName();
        String nameOfMovie = tickets.get(0).getScreening().getMovie().getEngName();
        GregorianCalendar screeningGeo = new GregorianCalendar(
                tickets.get(0).getScreening().getDate().get(Calendar.YEAR),
                tickets.get(0).getScreening().getDate().get(Calendar.MONTH),
                tickets.get(0).getScreening().getDate().get(Calendar.DAY_OF_MONTH),
                tickets.get(0).getScreening().getDate().get(Calendar.HOUR_OF_DAY),
                tickets.get(0).getScreening().getDate().get(Calendar.MINUTE));
        screeningGeo.add(Calendar.DAY_OF_MONTH,7);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm"); //set a date format
        String screeningDate = format.format(screeningGeo.getTime());
        String ticketsString = "";
        for(int i=0; i<tickets.size(); i++)
        {
            String num = String.valueOf(i+1);
            String ticketId = String.valueOf(tickets.get(i).getId());
            String seat = "(" + tickets.get(i).getRow() + ", " + tickets.get(i).getCol() + ")";
            ticketsString = ticketsString + "<tr>" +
                    "<td>" + num + "</td>" +
                    "<td>" + ticketId + "</td>" +
                    "<td>" + seat + "</td>" +
                    "<td>" + nameOfCinema + "</td>" +
                    "<td>" + nameOfTheater + "</td>" +
                    "<td>" + nameOfMovie + "</td>" +
                    "<td>" + screeningDate + "</td>" + "</tr>";
        }
            App.getOcsfClient(this).sendMail(
                    tickets.get(0).getPayment().getEmail(),
                    "Confirmed Order",
                    "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello "
                            + firstNameOfCustomer + " "
                            + lastNameOfCustomer +",</i></h1>" +
                            "<br><h2 style=\"color:black;\">Thanks for your purchase!</h2>" +
                            "<br><h3 style=\"color:black;\">Your order is confirmed.</h3> "+
                            "<br><table align=\"center\" border='2' dir=\"ltr\" td style=\"text-align:center\">" +
                            "<tr>" +
                            "<th> - </th>" +
                            "<th><font color=\"" + color + "\">OrderID</font></th>" +
                            "<th><font color=\"" + color + "\">Seat</font></th>" +
                            "<th><font color=\"" + color + "\">Cinema</font></th>" +
                            "<th><font color=\"" + color + "\">Theater</font></th>" +
                            "<th><font color=\"" + color + "\">Movie</font></th>" +
                            "<th><font color=\"" + color + "\">Screening</font></th>" +
                            "</tr>" +
                            ticketsString +
                            "</table dir=\"ltr\">" +
                            "</bdo>"
            );
    }

    private void sendMail(List<Ticket> tickets,int remain){
        String color = "blue";
        String firstNameOfCustomer = tickets.get(0).getPayment().getFirstName();
        String lastNameOfCustomer = tickets.get(0).getPayment().getLastName();
        String nameOfCinema = tickets.get(0).getScreening().getTheater().getCinema().getName();
        String nameOfTheater = tickets.get(0).getScreening().getTheater().getPlaceName();
        String nameOfMovie = tickets.get(0).getScreening().getMovie().getEngName();
        GregorianCalendar screeningGeo = new GregorianCalendar(
                tickets.get(0).getScreening().getDate().get(Calendar.YEAR),
                tickets.get(0).getScreening().getDate().get(Calendar.MONTH),
                tickets.get(0).getScreening().getDate().get(Calendar.DAY_OF_MONTH),
                tickets.get(0).getScreening().getDate().get(Calendar.HOUR_OF_DAY),
                tickets.get(0).getScreening().getDate().get(Calendar.MINUTE));
        screeningGeo.add(Calendar.DAY_OF_MONTH,7);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm"); //set a date format
        String screeningDate = format.format(screeningGeo.getTime());
        String ticketsString = "";
        for(int i=0; i<tickets.size(); i++)
        {
            String num = String.valueOf(i+1);
            String ticketId = String.valueOf(tickets.get(i).getId());
            String seat = "(" + tickets.get(i).getRow() + ", " + tickets.get(i).getCol() + ")";
            ticketsString = ticketsString + "<tr>" +
                    "<td>" + num + "</td>" +
                    "<td>" + ticketId + "</td>" +
                    "<td>" + seat + "</td>" +
                    "<td>" + nameOfCinema + "</td>" +
                    "<td>" + nameOfTheater + "</td>" +
                    "<td>" + nameOfMovie + "</td>" +
                    "<td>" + screeningDate + "</td>" + "</tr>";
        }
        App.getOcsfClient(this).sendMail(
                tickets.get(0).getPayment().getEmail(),
                "Confirmed Order",
                "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello "
                        + firstNameOfCustomer + " "
                        + lastNameOfCustomer +",</i></h1>" +
                        "<br><h2 style=\"color:black;\">Thanks for your purchase!</h2>" +
                        "<br><h3 style=\"color:black;\">Your order is confirmed. You remain "+remain +" in your Tickets Package</h3> "+
                        "<br><table align=\"center\" border='2' dir=\"ltr\" td style=\"text-align:center\">" +
                        "<tr>" +
                        "<th> - </th>" +
                        "<th><font color=\"" + color + "\">OrderID</font></th>" +
                        "<th><font color=\"" + color + "\">Seat</font></th>" +
                        "<th><font color=\"" + color + "\">Cinema</font></th>" +
                        "<th><font color=\"" + color + "\">Theater</font></th>" +
                        "<th><font color=\"" + color + "\">Movie</font></th>" +
                        "<th><font color=\"" + color + "\">Screening</font></th>" +
                        "</tr>" +
                        ticketsString +
                        "</table dir=\"ltr\">" +
                        "</bdo>"
        );
    }

    private void sendMail(Link link) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm");//set a date format
        GregorianCalendar to = new GregorianCalendar(link.getDate().get(Calendar.YEAR),link.getDate().get(Calendar.MONTH),link.getDate().get(Calendar.DAY_OF_MONTH)+7,link.getDate().get(Calendar.HOUR_OF_DAY),link.getDate().get(Calendar.MINUTE));
        App.getOcsfClient(this).sendMail(
                "galuk3@gmail.com, "+link.getPayment().getEmail(),
                "Order Confirmed",
                "<bdo dir=\"ltr\"><h1 style=\"color:orange;\"><i>Hello "+link.getPayment().getFirstName()+" "+ link.getPayment().getLastName()+"</i></h1><br>" +
                        "<br><h2 style=\"color:black;\">Thanks for your purchase!</h2>" +
                        "<br><h3 style=\"color:black;\">Your order is confirmed. Your order No. is  "+ link.getId() +"</h3> "+
                        "<br><table border='1' dir=\"ltr\">\n" +
                        "    <tr>\n" +
                        "      <td>movie</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "      <td>"+link.getMovie().getEngName()+"</td>\n" +
                        "      <td> The link will be available between: "+format.format(link.getDate().getTime().getTime()).toString()+" to: "+format.format(to.getTime()).toString() +"</td>\n" +
                        "    </tr></table dir=\"ltr\">" +
                        "</bdo>");
    }

    private enum SeatStatus {
        AVAILABLE,
        BLOCKED,
        SELECTED
    }

    protected void handleBlockSeatResponse(ResponseStatus responseStatus) {
        switch (responseStatus) {
            case Acknowledged -> setSeatStatus(SeatStatus.SELECTED);
            case Rejected -> setSeatStatus(SeatStatus.BLOCKED);
        }
    }

    private void setSeatStatus(SeatStatus seatStatus) {
        switch(seatStatus) {
            case AVAILABLE -> {
                seatBlockAttemptImage.setImage(new Image("ChackedSeat.png", 30,30,false,false));
                ticket.setCustomer((Customer)App.getUser());
                tickets.add(ticket);
                pickSeats--;
            }
            case BLOCKED -> seatBlockAttemptImage.setImage(new Image("BusySeat.png", 30,30,false,false));
        }

    }
}

