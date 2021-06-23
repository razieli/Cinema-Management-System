/**
 * Sample Skeleton for 'Payment.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PaymentController {

    @FXML // fx:id="titleText"
    private Text titleText; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="payButton"
    private Button payButton; // Value injected by FXMLLoader

    @FXML // fx:id="movieInfo"
    private Text movieInfo; // Value injected by FXMLLoader

    @FXML // fx:id="screeningInfo"
    private Text screeningInfo; // Value injected by FXMLLoader

    @FXML // fx:id="timeInfo"
    private Text timeInfo; // Value injected by FXMLLoader

    @FXML // fx:id="firstName"
    private TextField firstName; // Value injected by FXMLLoader

    @FXML // fx:id="lastName"
    private TextField lastName; // Value injected by FXMLLoader

    @FXML // fx:id="address"
    private TextField address; // Value injected by FXMLLoader

    @FXML // fx:id="city"
    private TextField city; // Value injected by FXMLLoader

    @FXML // fx:id="cardOwnerName"
    private TextField cardOwnerName; // Value injected by FXMLLoader

    @FXML // fx:id="cardOwnerLastName"
    private TextField cardOwnerLastName; // Value injected by FXMLLoader

    @FXML // fx:id="cardNumber"
    private TextField cardNumber; // Value injected by FXMLLoader

    @FXML // fx:id="cardExpiration"
    private DatePicker cardExpiration; // Value injected by FXMLLoader

    @FXML // fx:id="cvvNumber"
    private TextField cvvNumber; // Value injected by FXMLLoader

    @FXML // fx:id="totalPrice"
    private Text totalPrice; // Value injected by FXMLLoader

    @FXML
    void handheldsBackButton(ActionEvent event) {

    }

    @FXML
    void handheldsMovieDelete(MouseEvent event) {

    }

    @FXML
    void handheldsPay(ActionEvent event) {

    }

}
