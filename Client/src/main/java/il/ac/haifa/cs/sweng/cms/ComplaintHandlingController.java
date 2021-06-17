package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for complaint handling.
 */
public class ComplaintHandlingController implements Initializable {

    private static final double MAX_COMPENSATION = 100;

    @FXML
    private ListView<Complaint> complaintListView;

    @FXML
    private Text compensation;

    @FXML
    private Text reply;

    private List<Complaint> complaints;

    /**
     * Sends the complaint reply to the server.
     * Shows an error if any of the fields did not pass verification.
     */
    protected void replyToComplaint() {
        if(!verifyInput()) {
            // TODO: show error in GUI.
        } else {
            Date date = new Date();
            Complaint complaint = complaintListView.getSelectionModel().getSelectedItem();
            complaint.closeComplaint(date, reply.getText(), Double.parseDouble(compensation.getText()));
            App.getOcsfClient(this).replyToComplaint(complaint);
        }
    }
    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("EmployeeHome.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Verifies the complaint reply data the user has entered.
     * @return True if data is verified, false otherwise.
     */
    private boolean verifyInput() {
        double comp = 0;
        try {
            comp = Double.parseDouble(compensation.getText());
        } catch (NumberFormatException e) {
            return false;
        }

        if(comp < 0 || comp > MAX_COMPENSATION) {
            return false;
        }

        if(reply.getText().isEmpty()) {
            return false;
        }

        if(complaintListView.getSelectionModel().isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.getOcsfClient(this).getListOfComplaints(null);
        while(complaints == null) {
            Thread.yield();
        }
        updateComplaintList();
    }

    private void updateComplaintList() {
        this.complaintListView.getItems().addAll(complaints);
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
}
