/**
 * Sample Skeleton for 'SuccessfulPurchase.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class SuccessfulPurchaseController {

    private static Payment payment=null;

    @FXML // fx:id="successfulVBox"
    private VBox successfulVBox; // Value injected by FXMLLoader

    @FXML // fx:id="PurchaseIDOverview"
    private Text PurchaseIDOverview; // Value injected by FXMLLoader

    @FXML // fx:id="TopMenu"
    private AnchorPane TopMenu; // Value injected by FXMLLoader

    @FXML
    void handheldsBackButton(ActionEvent event) {

    }

    @FXML
    void handheldsBackToMovieList(ActionEvent event) {
        try {
            // storing the selected film to customise the newly created scene
            App.setRoot("ViewMovies.fxml");//load edit movie screen
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
}
