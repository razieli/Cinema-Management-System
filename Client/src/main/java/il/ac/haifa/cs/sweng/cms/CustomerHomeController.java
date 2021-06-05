package il.ac.haifa.cs.sweng.cms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerHomeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button logOut;

    @FXML
    private Text inputEngTitle;

    @FXML
    private Button editProfile;

    @FXML
    private Button moveList;

    @FXML
    private Button buyPackage;

    @FXML
    private Button buyLink;

    @FXML
    private Button cancelTicket;

    @FXML
    private Button fillComplaint;

}
