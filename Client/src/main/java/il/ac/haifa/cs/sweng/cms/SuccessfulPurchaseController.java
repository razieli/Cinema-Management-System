/**
 * Sample Skeleton for 'SuccessfulPurchase.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.entities.Payment;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SuccessfulPurchaseController {

    private static Payment payment=null;

    @FXML // fx:id="successfulVBox"
    private VBox successfulVBox; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseIDOverview"
    private Text PurchaseIDOverview; // Value injected by FXMLLoader

    @FXML // fx:id="TopMenu"
    private AnchorPane TopMenu; // Value injected by FXMLLoader


    @FXML
    private Label TheaterLabel;

    @FXML
    private Label MovieLabel;

    @FXML
    private Label NumLabel;

    @FXML
    private Label PlacesLabel;


    @FXML
    void handheldsBackButton(ActionEvent event) {
            try {
                // storing the selected film to customise the newly created scene
                App.setRoot("CustomerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    @FXML
    void handheldsBackToMovieList(ActionEvent event) {
        try {
            // storing the selected film to customise the newly created scene
            App.setRoot("CustomerHome.fxml");//load edit movie screen
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Payment getPayment() {
        return payment;
    }

    public static void setPayment(Payment payment) {
        SuccessfulPurchaseController.payment = payment;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(getPayment().getLinkList().isEmpty())
            {

                int ticketLength=((Customer)App.getUser()).getTicket().size();
            TheaterLabel.setText(((Customer)App.getUser()).getTicket().get(ticketLength-1).getScreening().getTheater().getName());
            MovieLabel.setText(((Customer)App.getUser()).getTicket().get(ticketLength-1).getScreening().getMovie().getEngName());
            NumLabel.setText((String.valueOf((((Customer)App.getUser()).getTicket().get(ticketLength-1).getScreening()
                    .getTheater().getSeatsCapacity()))));
                List<Ticket> ticketList=((Customer)App.getUser()).getPayment().getTicketList() ;
                Screening lastscreen = ((Customer) App.getUser()).getTicket(). ;
                for(int i=ticketList.size()-1;i>=0;i++)
                {
                    PlacesLabel.setText(PlacesLabel.getText()+" "+""+);
                }

            }
        else
            if(getPayment().getTicketList().isEmpty()){
                TheaterLabel = new Label(payment.getLinkList().get(0).getMovie().getEngName());
                MovieLabel = new Label(" The link will be available between:");
                NumLabel = new Label(payment.getLinkList().get(0).getDate().getTime().toString()+" to ");
                PlacesLabel =new Label(payment.getLinkList().get(0).getDate().getTime().toString()); //to

            }


    }
    }

