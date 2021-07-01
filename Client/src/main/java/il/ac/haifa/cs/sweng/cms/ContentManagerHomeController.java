package il.ac.haifa.cs.sweng.cms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentManagerHomeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloUserName.setText("Hello " + App.getUserName() + " !");
    }

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button movieListBtn;

    @FXML
    private Text helloUserName;

    @FXML
    void logOut(ActionEvent event) throws IOException {
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
    void switchToMovieListScreen(ActionEvent event) {
        try {
            App.setRoot("ViewMovies.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToPriceChangeSubmissionScreen(ActionEvent event) {
        try {
            App.setRoot("PriceChangeSubmission.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
