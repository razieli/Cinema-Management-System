package il.ac.haifa.cs.sweng.cms;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionSettingsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScreen();
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML // fx:id="ipText"
    private TextField ipText; // Value injected by FXMLLoader

    @FXML // fx:id="portText"
    private TextField portText; // Value injected by FXMLLoader

    @FXML // fx:id="save"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML
    void backBtn(ActionEvent event) {
        try {
            App.setRoot("UserLogin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveBtn(ActionEvent event) {
        App.setHost(ipText.getText());
        App.setPort(Integer.valueOf(portText.getText()));
        try {
            App.setRoot("UserLogin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateScreen(){
        ipText.setText(App.getHost());
        portText.setText(App.getPort().toString());
    }

}
