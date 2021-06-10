package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for complaint filing.
 */
public class ComplaintAddController implements Initializable {

    @FXML
    private Text name;

    @FXML
    private Text subject;

    @FXML
    private Text body;

    /**
     * Sends the complaint the user entered to the server.
     * Shows an error if any of the fields did not pass verification.
     */
    protected void addComplaint() {
        if(!verifyInput()) {
            // TODO: show error in GUI.
        } else {
            Date date = new Date();
            Complaint complaint = new Complaint(date, name.getText(), subject.getText(), body.getText());
            App.getOcsfClient(this).fileComplaint(complaint);
        }
    }

    /**
     * Verifies the complaint data the user has entered.
     * @return True if data is verified, false otherwise.
     */
    private boolean verifyInput() {
        if(name.getText().isEmpty()) {
            return false;
        }

        if(subject.getText().isEmpty()) {
            return false;
        }

        if(body.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
