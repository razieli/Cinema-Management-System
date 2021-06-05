package il.ac.haifa.cs.sweng.cms;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class userLoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public class PleaseProvideControllerClassName {

        @FXML
        private TextField userName;

        @FXML
        private TextField password;

        @FXML
        private Button connectBtn;

        @FXML
        private Button networkBtn;

        @FXML
        private Button registerBtn;

    }

}
