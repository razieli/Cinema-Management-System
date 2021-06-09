package il.ac.haifa.cs.sweng.cms;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloUserName.setText("Hello " + App.getName() + " !");
    }

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button movieListBtn;

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
    void switchToBuyLinkScreen(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("New features coming soon..  :)");
        alert.showAndWait();
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
            App.setRoot("ComplaintAdd.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToPurchasePackageScreen(ActionEvent event) {
        try {
            App.setRoot("ComplaintAdd.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
