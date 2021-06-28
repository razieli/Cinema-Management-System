/**
 * screen to overview picked movie and continue to purchase.
 * @parm movie, theater (potentially)
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

public class MovieOverviewController implements Initializable {
    private static Movie movie;
    private static Cinema pickedCinema = null;
    private static Screening pickedScreening = null;

    private LocalDate date=null;
    private String hour="";
    private GregorianCalendar gregorianCalendar =null;
    private List<Cinema> cinemas= new ArrayList<Cinema>();

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="inputEngTitle"
    private Text inputEngTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputHebTitle"
    private Text inputHebTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputTrailer"
    private Hyperlink inputTrailer; // Value injected by FXMLLoader

    @FXML // fx:id="inputLength"
    private Text inputLength; // Value injected by FXMLLoader

    @FXML // fx:id="inputYear"
    private Text inputYear; // Value injected by FXMLLoader

    @FXML // fx:id="inputPGRaiting"
    private Text inputPGRaiting; // Value injected by FXMLLoader

    @FXML // fx:id="inputImage"
    private ImageView inputImage; // Value injected by FXMLLoader

    @FXML // fx:id="stackPane"
    private StackPane stackPane; // Value injected by FXMLLoader

    @FXML // fx:id="chooseTicket"
    private VBox chooseTicket; // Value injected by FXMLLoader

    @FXML // fx:id="messageTicketTitle"
    private Text messageTicketTitle; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaComboBox"
    private ComboBox<Cinema> cinemaComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="screeningComboBox"
    private ComboBox<Screening> screeningComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="addScreeningButton"
    private Button addScreeningButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelTicket"
    private Button cancelTicket; // Value injected by FXMLLoader

    @FXML // fx:id="chooseLink"
    private VBox chooseLink; // Value injected by FXMLLoader

    @FXML // fx:id="messageLinkTitle"
    private Text messageLinkTitle; // Value injected by FXMLLoader

    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    @FXML // fx:id="hourComboBox"
    private ComboBox<LocalTime> hourComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="addTimeButton"
    private Button addTimeButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelLink"
    private Button cancelLink; // Value injected by FXMLLoader

    @FXML // fx:id="chooseViewing"
    private FlowPane chooseViewing; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="leftVBox"
    private VBox leftVBox; // Value injected by FXMLLoader

    @FXML // fx:id="inputCast"
    private Text inputCast; // Value injected by FXMLLoader

    @FXML // fx:id="inputDescription"
    private Text inputDescription; // Value injected by FXMLLoader

    /**
     * get movie object
     * @return movie
     */
    public static Movie getMovie() {
        return movie;
    }

    /**
     * set movie object
     * @param movie
     */
    public static void setMovie(Movie movie) {
        MovieOverviewController.movie = movie;
    }

    /**
     *
     * @return
     */
    public static Cinema getPickedCinema() {
        return pickedCinema;
    }

    /**
     *
     * @param pickedCinema
     */
    public static void setPickedCinema(Cinema pickedCinema) {
        MovieOverviewController.pickedCinema = pickedCinema;
    }


    /**
     * back button functionality
     */
    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("ViewMovies.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purchase Link button functionality
     */
    @FXML
    void handheldsPurchaseLink(ActionEvent event) {
//set stackPane
        stackPane.getChildren().remove(chooseLink);
        stackPane.getChildren().add(chooseLink);

        messageLinkTitle.setText("When would you like to watch \'"+movie.getEngName()+"\' ?"); //setup header
        /*set Combobox*/
        //set hour Combo box to be able to add new value
        hourComboBox.setEditable(true);

        /*set Combobox options*/
        //hour
        for(int i=0;i<=23;i++) {
            for (int j = 0; j < 60; j=j+15) {
                hourComboBox.getItems().add(LocalTime.of(i,j,0,0));
            }
        }

        /*on action listener for comboBox*/
        //date
        datePicker.setOnAction((e1) -> {
            date = datePicker.getValue();
        });

        //hour
        hourComboBox.setOnAction((e2) -> {
            hour = String.valueOf(hourComboBox.getValue());
        });

        addTimeButton.setOnAction(e3->{
            if (date==null || hour.isEmpty()) {
                //set a conformation alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle(null);
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("One or more sections is empty. Please make sure to fill the date and time.");
                errorAlert.showAndWait();

            } else {
                //add to screeningList
                String[] hourMin = hour.split(":", 2);//split the date string to hour and minuets
                gregorianCalendar = new GregorianCalendar(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]));


                if (gregorianCalendar.getTime().before(GregorianCalendar.getInstance().getTime())) {
                    //set a conformation alert
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle(null);
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("This date have passed please pick another date.");
                    errorAlert.showAndWait();
                    gregorianCalendar=null;

                } else {
                    // TODO: 27/06/2021 pass
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Remember");
                    alert.setHeaderText(null);
                    alert.setContentText("Remember the link to the movie is available for a week from the time you selected");
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        datePicker.setValue(null);
                        hourComboBox.setValue(null);

                        Link link = new Link((Customer)App.getUser() , gregorianCalendar, movie);

                        //todo: repalce with paymant screen when ready
//                    PaymentController.setLink(link);
//                    PaymentController.setFromScreen(3);//came from Link
//                        App.setRoot("Payment.fxml"); //set the sceen to the last page.
//                    App.setRoot("PaymentScreen.fxml"); //set the scean to the last page.
                    }

                }
            }
        });

        cancelLink.setOnAction(e4->{
            datePicker.setValue(null);
            hourComboBox.setValue(null);
            gregorianCalendar=null;
            stackPane.getChildren().remove(chooseViewing);
            stackPane.getChildren().add(chooseViewing);
        });
    }

    /**
     * Purchase Ticket button functionality
     */
    @FXML
    void handheldsPurchaseTicket(ActionEvent event) {
        //set stackPane
        stackPane.getChildren().remove(chooseTicket);
        stackPane.getChildren().add(chooseTicket);

        messageTicketTitle.setText("When and where would you like to watch \'"+movie.getEngName()+"\' ?");//setup header
        /*set Combobox options*/
        //Cinema
        for(Screening screening: movie.getScreening()){
            if (cinemas.isEmpty() || !cinemas.contains(screening.getTheater().getCinema())){
                cinemas.add(screening.getTheater().getCinema());
            }
        }
        if (!cinemas.isEmpty())
            cinemaComboBox.setItems(FXCollections.observableArrayList(cinemas));

        //Screening
        //if filter by cinema show pick by defult.
        if(pickedCinema != null) {
            cinemaComboBox.setValue(pickedCinema);

            //init theaterComboBox from picked cinema
            while (cinemas.isEmpty()) {
                Thread.yield();
            }
            List<Screening> screenList = new ArrayList<Screening>();
            for(Screening screening: movie.getScreening()){
                if(screening.getTheater().getCinema().equals(pickedCinema)){
                    screenList.add(screening);
                }
            }
            screeningComboBox.setItems(FXCollections.observableArrayList(screenList));
        }

        /*handlers on event*/
        //Cinema
        cinemaComboBox.setOnAction((e1) -> {
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
        screeningComboBox.setOnAction((e2) -> {
            pickedScreening = screeningComboBox.getValue();
        });

        addScreeningButton.setOnAction(e3->{
            // TODO: 27/06/2021 pass movie, screening
            if(pickedCinema==null || pickedScreening==null) {
                //set a conformation alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle(null);
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("One or more sections is empty.");
                errorAlert.showAndWait();
            }
            else {
                try {
                    PurchaseTicketController.setMovie(movie);
                    PurchaseTicketController.setScreening(pickedScreening);
                    cinemaComboBox.getItems().clear();//clean combobox
                    App.setRoot("PurchaseTicket.fxml"); //set the scean to the last page.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        cancelTicket.setOnAction(e4->{
            cinemaComboBox.setValue(null);
            pickedCinema=null;
            pickedScreening=null;
            stackPane.getChildren().remove(chooseViewing);
            stackPane.getChildren().add(chooseViewing);
        });


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         *set components size to adapt window size
         */
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            leftVBox.prefWidthProperty().bind(scrollPane.widthProperty());
        });

        scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            leftVBox.prefHeightProperty().bind(scrollPane.heightProperty());
        });
//        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
//            leftVBox.prefWidthProperty().bind(scrollPane.widthProperty());
//        });
//
//        scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
//            leftVBox.prefHeightProperty().bind(scrollPane.heightProperty());
//        });


        /*
         *set loaded text from the entity in the scene components
         */
        //set title to show on screen
        inputEngTitle.setText(movie.getEngName());
        inputHebTitle.setText(movie.getHebName());

        inputImage.setImage(new Image(movie.getPosterUrl().toString()));//set poster to show from url

        inputTrailer.setOnAction(new EventHandler<ActionEvent>() {
                                     @Override public void handle(ActionEvent e) {
                                         try {
                                             Desktop.getDesktop().browse(movie.getTrailerUrl());
                                         } catch (IOException e1) {
                                             e1.printStackTrace();
                                         }
                                     }
                                 }
        );

        inputYear.setText(String.valueOf(movie.getYear()));  //set year to show
        inputLength.setText(String.valueOf(movie.getLength()) + " min"); //set movie length to show on screen
        inputCast.setText(movie.getCastList());//set cast list to show on screen
        inputPGRaiting.setText(String.valueOf(movie.getAgeRestriction()));//set PG rating to show on screen
        inputDescription.setText(movie.getDescription()); //set description to show on screen
    }

}
