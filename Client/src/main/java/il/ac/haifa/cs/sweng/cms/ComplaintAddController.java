package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.App;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Controller for complaint filing.
 */
public class ComplaintAddController implements Initializable {

    protected static final long TIME_TO_RESPOND = 24;

    @FXML
    private ListView<Complaint> complaintListView;

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
        if (verifyInput()) {
            this.date = new Date();

            Complaint complaint = new Complaint(date, subject.getSelectionModel().getSelectedItem(), body.getText(), (Customer) App.getUser());
            App.getOcsfClient(this).fileComplaint(complaint);
            complaints.add(complaint);
        }
    }

    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("CustomerHome.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name().substring(0, 1).toUpperCase() + alertType.name().substring(1).toLowerCase());
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Verifies the complaint data the user has entered.
     *
     * @return True if data is verified, false otherwise.
     */
    private boolean verifyInput() {
        if (subject.getSelectionModel().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Complaint filing failed:", "Please select a subject.");
            return false;
        }

        if (body.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Complaint filing failed:", "Please enter complaint body.");
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
        while (complaints == null) {
            Thread.yield();
        }
        updateComplaintList();
        complaintListView.getSelectionModel().selectedItemProperty().addListener((observableValue, complaint, t1) -> updateSelectedComplaint(t1));
    }

    public void updateComplaintList() {
        complaints.removeIf(complaint -> !complaint.getCustomer().getUserName().equals(App.getUser().getUserName()));
        this.complaintListView.getItems().addAll(complaints);
    }

    private void updateSelectedComplaint(Complaint selected) {
        this.complaintListSubject.setText(selected.getSubject());
        Complaint.Status status = selected.getStatus();
        switch (status) {
            case FILED -> this.complaintListStatus.setText("Waiting");
            case CLOSED_WITH_COMP -> this.complaintListStatus.setText("Closed with compensation");
            case CLOSED_WITHOUT_COMP -> this.complaintListStatus.setText("Closed without compensation");
        }
        Date filingDate = selected.getFilingDate();
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

    public void handleComplaintFileResponse() {
        Platform.runLater(() -> this.complaintListView.getItems().add(complaints.get(complaints.size() - 1)));
        this.body.setText("");
        Platform.runLater(() -> showAlert(Alert.AlertType.INFORMATION, "Complaint filed successfully.", ""));
    }
}
