package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> subject;

    @FXML
    private Text body;

    @FXML
    private Text complaintListSubject;

    @FXML
    private Text complaintListStatus;

    @FXML
    private Text complaintListDate;

    @FXML
    private Text complaintListTtr;

    private Date date;

    /**
     * Sends the complaint the user entered to the server.
     * Shows an error if any of the fields did not pass verification.
     */
    @FXML
    protected void addComplaint() {
        if(!verifyInput()) {
            // TODO: show error in GUI.
        } else {
            this.date = new Date();

            Complaint complaint = new Complaint(date, subject.getSelectionModel().getSelectedItem(), body.getText(),null);
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

        if(subject.getSelectionModel().getSelectedItem().isEmpty()) {
            return false;
        }

        if(body.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subject.getItems().addAll(
                "Technical issue",
                "Noise",
                "Cleanliness",
                "Customer service"
        );
    }

    public void updateComplaintList() {
        this.complaintListSubject.setText(subject.getSelectionModel().getSelectedItem());
        this.complaintListStatus.setText("Waiting");
        this.complaintListDate.setText(date.toString());
        this.complaintListTtr.setText("24:00");
    }
}
