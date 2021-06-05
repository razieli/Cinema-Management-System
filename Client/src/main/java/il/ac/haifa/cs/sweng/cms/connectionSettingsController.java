package il.ac.haifa.cs.sweng.cms;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class connectionSettingsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private Button save;

    @FXML
    private Button back;

}
