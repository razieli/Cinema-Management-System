package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static il.ac.haifa.cs.sweng.cms.ComplaintAddController.TIME_TO_RESPOND;

/**
 * Controller for price changes handling.
 */
public class PriceChangesHandlingController implements Initializable {

    private static final double MAX_COMPENSATION = 100;

    @FXML
    private ListView<Complaint> complaintListView;

    @FXML
    private TextField compensation;

    @FXML
    private TextArea reply;

    @FXML
    private Text subject;

    @FXML
    private Text status;

    @FXML
    private Text date;

    @FXML
    private Text ttr;

    private List<Complaint> complaints;

    /**
     * Sends the complaint reply to the server.
     * Shows an error if any of the fields did not pass verification.
     */
    @FXML
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
            showAlert("Compensation must be a number.");
            return false;
        }

        if(comp < 0 || comp > MAX_COMPENSATION) {
            showAlert("Compensation must be between 0 and " + MAX_COMPENSATION + ".");
            return false;
        }

        if(reply.getText().isEmpty()) {
            showAlert("Reply body empty.");
            return false;
        }

        if(complaintListView.getSelectionModel().isEmpty()) {
            showAlert("No complaint selected.");
            return false;
        }

        if(complaintListView.getSelectionModel().getSelectedItem().getStatus() != Complaint.Status.FILED) {
            showAlert("Cannot reply to an already closed complaint.");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error while trying to send reply:");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.getOcsfClient(this).getListOfComplaints(null);
        while(complaints == null) {
            Thread.yield();
        }
        complaints.removeIf(complaint -> complaint.getStatus() != Complaint.Status.FILED);
        updateComplaintList();
        complaintListView.getSelectionModel().selectedItemProperty().addListener((observableValue, complaint, t1) -> updateSelectedComplaint(t1));
    }

    private void updateSelectedComplaint(Complaint selected) {
        this.subject.setText(selected.getSubject());
        Complaint.Status status = selected.getStatus();
        switch (status) {
            case FILED -> this.status.setText("Waiting");
            case CLOSED_WITH_COMP -> this.status.setText("Closed with compensation");
            case CLOSED_WITHOUT_COMP -> this.status.setText("Closed without compensation");
        }
        Date filingDate = selected.getFilingDate();
        this.date.setText(selected.getFilingDate().toString());
        Date currDate = new Date();
        long diff = currDate.getTime() - filingDate.getTime();
        TimeUnit time = TimeUnit.HOURS;
        diff = time.convert(diff, TimeUnit.MILLISECONDS);
        long timeLeft = TIME_TO_RESPOND - diff - 1;
        this.ttr.setText(timeLeft + " hours");
    }

    private void updateComplaintList() {
        this.complaintListView.getItems().addAll(complaints);
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public void onReplyReceived() {
        Complaint selected = this.complaintListView.getSelectionModel().getSelectedItem();
        updateSelectedComplaint(selected);
    }
}
