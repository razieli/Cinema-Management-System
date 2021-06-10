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

public class CancelLinkController implements Initializable {

    //test DB
    private Customer cus1 = new Customer("Customer1", "Customer1",null,null);
    private Customer cus2 = new Customer("Customer2", "Customer2",null,null);

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.YY E HH:mm");

    // javaFX buttons and fields
    @FXML
    private TextField movieName;

    @FXML
    private TextField screeningTime;

    @FXML
    private Button CancelTicket;

    @FXML // fx:id="Tickets"
    private ComboBox<Ticket> linksComboBox;


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
    public void handheldsCancelLink(ActionEvent actionEvent) {
        //set a warning alert
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // if a ticket was selected remove it from DB
        if(linksComboBox.getValue()!= null) {
            alert.setTitle("Link Cancel");
            alert.setHeaderText(null);
            alert.setContentText("Are You Sure you want to cancel selected Link?");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            //TODO: cancellation based on current time.
            if (alert.getResult() == ButtonType.YES)//delete operation from database
            {

                alert.setHeaderText(null);
                alert.setContentText("Link Canceled");
            } else {
                alert.setHeaderText(null);
                alert.setContentText("Link Did Not Canceled");
            }
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.OK);
            alert.showAndWait();
            updateScreen();
        }
        else{
            alert.setHeaderText(null);
            alert.setContentText("No Link Selected");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.OK);
            alert.showAndWait();
        }
    }


    /**
     * handle combobox selection
     */
    public void handheldsSelectLink(ActionEvent actionEvent) {
        if (linksComboBox.getValue() != null) {
            //setting right screen based on TicketsCoboBox selection

        }
    }

    /**
     * reset text fields
     */
    public void resetTexts(){
        movieName.setText("Movie Name");
        screeningTime.setText("dd.MM.YY E HH:mm");

    }


    /**
     * update scene components
     */
    public void updateScreen(){
        resetTexts();


    }


    /**
     * initializing scene components
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing TicketsCoboBox


        updateScreen();
    }





}
