<<<<<<< Updated upstream
package il.ac.haifa.cs.sweng.cms;
GAGSDF
import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class CancelLinkController implements Initializable {

    public List<Link> linksList = new ArrayList<Link>();
    private List<Customer> customerList= new ArrayList<Customer>();
    private List<Movie> movieList= new ArrayList<Movie>();

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm");

    // javaFX buttons and fields
    @FXML
    private TextField movieName;

    @FXML
    private TextField Time;

    @FXML
    private Button CancelLink;

    @FXML // fx:id="Tickets"
    private ComboBox<Link> linksComboBox;


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
                linksList.remove(linksComboBox.getValue());
                App.getOcsfClient(this).updateLinks(linksList);
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
            movieName.setText(linksComboBox.getValue().getMovie().getEngName());
            Time.setText(format.format(linksComboBox.getValue().getDate().getTime()));
        }
    }

    /**
     * reset text fields
     */
    public void resetTexts(){
        movieName.setText("Movie Name");
        Time.setText("dd.MM.YY E HH:mm");
    }


    /**
     * update scene components
     */
    public void updateScreen(){
        resetTexts();
        linksComboBox.setItems(FXCollections.observableArrayList(linksList));
    }


    /**
     * initializing scene components
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Customer customer = (Customer) App.getUser();
            App.getOcsfClient(this).getListOfLinks();
            while(linksList.isEmpty()) { Thread.yield(); }
            List <Link> links = new ArrayList<Link>();
            for (Link link : linksList) {
                if(link.getCustomer().getUserName().equals(customer.getUserName())){
                    links.add(link);
                }
            }
            linksList = links;
            updateScreen();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setLinks(List<Link> links) {
        this.linksList = links;
    }

}
=======
package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;SDFSDFSDFSDF
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class CancelLinkController implements Initializable {

    public List<Link> linksList = new ArrayList<Link>();
    private List<Customer> customerList= new ArrayList<Customer>();
    private List<Movie> movieList= new ArrayList<Movie>();

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm");

    // javaFX buttons and fields
    @FXML
    private TextField movieName;

    @FXML
    private TextField Time;

    @FXML
    private Button CancelLink;

    @FXML // fx:id="Tickets"
    private ComboBox<Link> linksComboBox;


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
                App.getOcsfClient(this).updateLinks(linksComboBox.getValue(), false);
                linksList.remove(linksComboBox.getValue());
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
            movieName.setText(linksComboBox.getValue().getMovie().getEngName());
            Time.setText(format.format(linksComboBox.getValue().getDate().getTime()));
        }
    }

    /**
     * reset text fields
     */
    public void resetTexts(){
        movieName.setText("Movie Name");
        Time.setText("dd.MM.YY E HH:mm");
    }


    /**
     * update scene components
     */
    public void updateScreen(){
        resetTexts();
        linksComboBox.setItems(FXCollections.observableArrayList(linksList));
    }


    /**
     * initializing scene components
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Customer customer = (Customer) App.getUser();
            App.getOcsfClient(this).getListOfLinks();
            while(linksList.isEmpty()) { Thread.yield(); }
            List <Link> links = new ArrayList<Link>();
            for (Link link : linksList) {
                if(link.getCustomer().getUserName().equals(customer.getUserName())){
                    links.add(link);
                }
            }
            linksList = links;
            updateScreen();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setLinks(List<Link> links) {
        this.linksList = links;
    }

}
>>>>>>> Stashed changes
