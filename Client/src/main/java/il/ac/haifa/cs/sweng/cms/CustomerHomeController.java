package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerHomeController implements Initializable {



    @FXML
    private Button LogOutBtn;

    @FXML
    private Button movieListBtn;

    @FXML
    private Text creditStatus;

    @FXML
    private Text packageStatus;

    @FXML
    private Button buyLinkBtn;

    @FXML
    private Button buyPackageBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button supportBtn;

    @FXML
    private Text helloUserName;

    @FXML
    void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
        {
            try {
                App.disconnect();
                App.setRoot("UserLogin.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void switchToAddComplaintScreen(ActionEvent event) {
        try {
            App.setRoot("ComplaintAdd.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToCancelScreen(ActionEvent event) {
        try {
            App.setRoot("PurchaseCancel.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToMovieListScreen(ActionEvent event) {
        try {
            App.setRoot("ViewMovies.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToPurchasePackageScreen(ActionEvent event) {
        try {
            PaymentController.setFromScreen(3);
            App.setRoot("Payment.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer customer=(Customer)App.getUser();
        helloUserName.setText("Hello " + App.getUserName() + " !");
        if (customer.getBalance()>0)
            creditStatus.setText("Credit: " + customer.getBalance());
        else
            creditStatus.setText("Credit: 0");

        if(customer.isHas_package())
            packageStatus.setText("Your package has "+customer.getPackageTicketsRemaining() +" more ticket to use.");
        else
            packageStatus.setText("No package has directed.");

    }

}
