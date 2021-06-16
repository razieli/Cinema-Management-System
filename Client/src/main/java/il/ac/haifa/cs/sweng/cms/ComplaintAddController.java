package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Controller for complaint filing.
 */
public class ComplaintAddController implements Initializable {

    private static final long TIME_TO_RESPOND = 24;

    @FXML
    private Label name;

    @FXML
    private ComboBox<String> subject;

    @FXML
    private TextField body;

    @FXML
    private Text complaintListSubject;

    @FXML
    private Text complaintListStatus;

    @FXML
    private Text complaintListDate;

    @FXML
    private Text complaintListTtr;

    private Date date;

    private List<Complaint> complaints;

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

            Complaint complaint = new Complaint(date, subject.getSelectionModel().getSelectedItem(), body.getText(), (Customer) App.getUser());
            App.getOcsfClient(this).fileComplaint(complaint);
            complaints.add(complaint);
        }
    }

    /**
     * Verifies the complaint data the user has entered.
     * @return True if data is verified, false otherwise.
     */
    private boolean verifyInput() {
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
        name.setText(App.getUser().getUserName());
        subject.getItems().addAll(
                "Technical issue",
                "Noise",
                "Cleanliness",
                "Customer service"
        );
        App.getOcsfClient(this).getListOfComplaints(App.getUser());
        while(complaints == null) {
            Thread.yield();
        }
        updateComplaintList();
    }

    public void updateComplaintList() {
        this.complaintListSubject.setText(complaints.get(complaints.size() - 1).getSubject());
        Complaint.Status status = complaints.get(complaints.size() - 1).getStatus();
        switch (status) {
            case FILED -> this.complaintListStatus.setText("Waiting");
            case CLOSED_WITH_COMP -> this.complaintListStatus.setText("Closed with compensation");
            case CLOSED_WITHOUT_COMP -> this.complaintListStatus.setText("Closed without compensation");
        }
        Date filingDate = complaints.get(complaints.size() - 1).getFilingDate();
        this.complaintListDate.setText(filingDate.toString());
        Date currDate = new Date();
        long diff = currDate.getTime() - filingDate.getTime();
        TimeUnit time = TimeUnit.HOURS;
        diff = time.convert(diff, TimeUnit.MILLISECONDS);
        long timeLeft = TIME_TO_RESPOND - diff - 1;
        this.complaintListTtr.setText(timeLeft + " hours");
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
}
