/**
 * Sample Skeleton for 'PurchaseTicket.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.entities.Theater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseTicketController implements Initializable {
    private static Movie movie;
    private static Theater theater = null;

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="inputEngTitle"
    private Text inputEngTitle; // Value injected by FXMLLoader

    @FXML // fx:id="paymentButton"
    private Button paymentButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageTitle"
    private Label messageTitle; // Value injected by FXMLLoader

    @FXML // fx:id="screeningButtonBar"
    private ButtonBar screeningButtonBar; // Value injected by FXMLLoader

    @FXML // fx:id="addScreeningButton"
    private Button addScreeningButton; // Value injected by FXMLLoader

    @FXML // fx:id="theaterChoiceBox"
    private ChoiceBox<Theater> theaterChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="seatChoiceBox"
    private ChoiceBox<?> seatChoiceBox; // Value injected by FXMLLoader

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

    public static Movie getMovie() {
        return movie;
    }

    public static void setMovie(Movie movie) {
        PurchaseTicketController.movie = movie;
    }

    public static Theater getTheater() {
        return theater;
    }

    public static void setTheater(Theater theater) {
        PurchaseTicketController.theater = theater;
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

    }

    @FXML
    void handheldsPayment(ActionEvent event) {

    }

    public static void loadScreening()
    {
//        if (theater != null){
//            if(!screeningChoiceBox.getItems().isEmpty())
//                screeningChoiceBox.getItems().clear();
//
//                for (Screening screen : theater.getScreeningList()){
//                    screeningChoiceBox.getItems().add(screen);
//                }
//                screeningButtonBar.getButtons().add(screeningChoiceBox);
//            }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageTitle.setText("When would you like to watch \'"+movie.getEngName()+"\' ?");

//        for (choiceTheater : theaters) {
//            theaterChoiceBox.getItems().add(choiceTheater);
//        }
//
//        loadScreening();
//
//      theaterChoiceBox.setOnMouseClicked(e->{
//            theater=theaterChoiceBox.getValue();
//           loadScreening();
//        });



    }
}
