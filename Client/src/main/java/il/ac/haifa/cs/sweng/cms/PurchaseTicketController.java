/**
 * Sample Skeleton for 'PurchaseTicket.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseTicketController implements Initializable {
    private static Movie movie;
    private static Cinema pickedCinema = null;
    private static Screening pickedScreening = null;
    private static int pickSeats = 0;
//    private List<Cinema> cinemas= new ArrayList<Cinema>();
    private List<Cinema>cinemas= ViewMoviesController.getCinemas();

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
            pickedScreening = screeningComboBox.getValue();
            System.out.println(pickedScreening);
            int[][] seatsMap = pickedScreening.getSeats();
            for(int row=0;row <= pickedScreening.getSeatsCapacity()/10 ; row++){
                for(int col=0 ; col < 10 ; col++){
                    try {
                        addSeat(pickedScreening,row,col);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //seats
        seatComboBox.setOnMouseClicked(e->{
           pickSeats=seatComboBox.getValue();
        });



    }

    protected void addSeat(Screening screening, int row, int col) throws FileNotFoundException {
        int[][] seatMap = screening.getSeats();
        ImageView imageView = new ImageView();
        if(seatMap[row][col] == 0){
            // TODO: 22/06/2021 toggle collor,   max seat to select
            imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/FreeSeat.png")));
        }

        else{
            imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/BusySeat.png")));
        }

        seatGridPane.add(imageView,col,row);
        // TODO: 22/06/2021  send taken seat to server 
        imageView.setOnMouseClicked(e -> {
            try {

                imageView.setImage(new Image(new FileInputStream("Client/src/main/resourses/ChackedSeat.png")));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

        });
    }

    public static String intToLetters(int num) {
        String numberInLetter = null;
        switch (num) {
            case 1:
                numberInLetter = "A";
                break;
            case 2:
                numberInLetter = "B";
                break;
            case 3:
                numberInLetter = "C";
                break;
            case 4:
                numberInLetter = "D";
                break;
            case 5:
                numberInLetter = "E";
                break;
            case 6:
                numberInLetter = "F";
                break;
            case 7:
                numberInLetter = "G";
                break;
            case 8:
                numberInLetter = "H";
                break;
            case 9:
                numberInLetter = "I";
                break;
            case 10:
                numberInLetter = "J";
                break;
        }
        return numberInLetter;
    }

//    public List<Cinema> getCinemas() {
//        return cinemas;
//    }
//
//    public void setCinemas(List<Cinema> cinemas) {
//        this.cinemas = cinemas;
//    }
}
