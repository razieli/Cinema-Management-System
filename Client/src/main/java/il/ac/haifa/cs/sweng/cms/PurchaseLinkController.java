package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class PurchaseLinkController implements Initializable {

    private static Movie movie;
    private String date="";
    private String hour="";

    private GregorianCalendar gregorianCalendar =null;

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="inputEngTitle"
    private Text inputEngTitle; // Value injected by FXMLLoader

    @FXML // fx:id="paymentButton"
    private Button paymentButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageTitle"
    private Label messageTitle; // Value injected by FXMLLoader

    @FXML // fx:id="addTimeButton"
    private Button addTimeButton; // Value injected by FXMLLoader

    @FXML // fx:id="hourComboBox"
    private ComboBox<LocalTime> hourComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    @FXML // fx:id="dateConfirmation"
    private Text dateConfirmation; // Value injected by FXMLLoader

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
        PurchaseLinkController.movie = movie;
    }

    /**
     * back button functionality
     */
    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("MovieOverview.fxml"); //set the screan to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handheldsAddTimeButton(ActionEvent event) {
        if (date.isEmpty() || hour.isEmpty()) {
            //set a conformation alert
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(null);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("One or more sections is empty. Please make sure to fill the date and time.");
            errorAlert.showAndWait();

        } else {
            //add to screeningList
            String[] dateSplit = date.split("-", 3);//split the date string to year,mount and day
            String[] hourMin = hour.split(":", 2);//split the date string to hour and minuets
            gregorianCalendar = new GregorianCalendar(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[2]), Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]));


            if (gregorianCalendar.getTime().before(GregorianCalendar.getInstance().getTime())) {
                //set a conformation alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle(null);
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("This date have passed please pick another date.");
                errorAlert.showAndWait();
                gregorianCalendar=null;

            } else {
                dateConfirmation.setText("You chose to watch the movie \'" + movie.getEngName() + "\' at " + hour + " on " + date);
                datePicker.setValue(null);
                hourComboBox.setValue(null);
                date = "";
                hour = "";

            }
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        messageTitle.setText("When would you like to watch \'"+movie.getEngName()+"\' ?");

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
        datePicker.setOnAction((event) -> {
            date = String.valueOf(datePicker.getValue());
        });

        //hour
        hourComboBox.setOnAction((event) -> {
            hour = String.valueOf(hourComboBox.getValue());
        });

    }


    @FXML
    void handheldsPayment(ActionEvent event) {
        if (gregorianCalendar==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You didn't pick a date");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.OK);
            alert.showAndWait();

        }

        else{

            try {
                Customer customer = (Customer)App.getUser();

                Link movieLink = new Link (customer, gregorianCalendar,movie);
//              PaymentScreenController.setMovieLink(movieLink);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Remember");
                alert.setHeaderText(null);
                alert.setContentText("Remember the link to the movie is available for a week from the time you selected");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    Link link = new Link(null , gregorianCalendar, movie);

                    //todo: repalce with paymant screen when ready
//                    PaymentScreenController.getLink(link);
                    App.setRoot("MovieOverview.fxml"); //set the scean to the last page.
//                    App.setRoot("PaymentScreen.fxml"); //set the scean to the last page.
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
