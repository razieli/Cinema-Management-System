/**
 * screen to overview picked movie and continue to purchase.
 * @parm movie, theater (potentially)
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Theater;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieOverviewController implements Initializable {
    private static Movie movie;
    private static Cinema pickedCinema = null;

    @FXML // fx:id="inputEngTitle"
    private Text inputEngTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputHebTitle"
    private Text inputHebTitle; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="purchaseLinkButton"
    private Button purchaseLinkButton; // Value injected by FXMLLoader

    @FXML // fx:id="purchaseTicketButton"
    private Button purchaseTicketButton; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="anchorScrollPane"
    private AnchorPane anchorScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="inputImage"
    private ImageView inputImage; // Value injected by FXMLLoader

    @FXML // fx:id="inputTrailer"
    private Hyperlink inputTrailer; // Value injected by FXMLLoader

    @FXML // fx:id="inputLength"
    private Text inputLength; // Value injected by FXMLLoader

    @FXML // fx:id="inputYear"
    private Text inputYear; // Value injected by FXMLLoader

    @FXML // fx:id="inputPGRaiting"
    private Text inputPGRaiting; // Value injected by FXMLLoader

    @FXML // fx:id="inputCast"
    private Text inputCast; // Value injected by FXMLLoader

    @FXML // fx:id="inputDescription"
    private Text inputDescription; // Value injected by FXMLLoader

    @FXML // fx:id="inputScreening"
    private Text inputScreening; // Value injected by FXMLLoader

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
        //todo: add FXML to move to the tickets
        try {
            PurchaseLinkController.setMovie(movie);
            App.setRoot("PurchaseLink.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purchase Ticket button functionality
     */
    @FXML
    void handheldsPurchaseTicket(ActionEvent event) {
        //todo: add FXML to move to the tickets

        try {
//            if (pickedCinema!=null){
//                PurchaseTicketController.setCinema(pickedCinema);
//            }

            if(pickedCinema != null)
                PurchaseTicketController.setPickedCinema(pickedCinema);

            PurchaseTicketController.setMovie(movie);
            App.setRoot("PurchaseTicket.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         *set components size to adapt window size
         */
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            anchorScrollPane.prefWidthProperty().bind(scrollPane.widthProperty());
        });

        scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            anchorScrollPane.prefHeightProperty().bind(scrollPane.heightProperty());
        });

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
