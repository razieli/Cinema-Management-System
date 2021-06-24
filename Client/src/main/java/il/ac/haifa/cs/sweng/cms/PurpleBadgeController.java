package il.ac.haifa.cs.sweng.cms;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import il.ac.haifa.cs.sweng.cms.common.entities.PurpleBadge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class PurpleBadgeController implements Initializable {
	private int permission= App.getUserPermission();
	private PurpleBadge pb = App.getPb();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button backButton;

	@FXML
	private ToggleButton toggleSwitch;

	@FXML
	private Text capacityText;

	@FXML
	private Button editButton;

	@FXML
	private TextField capacityAmount;

	@FXML
	private Button okButton;

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
    private Text FromText;

	@FXML
    private Text toText;

	@FXML
	void editCapacity(ActionEvent event) {
		editButton.setVisible(false);
		capacityText.setText("Set maximum capacity");
		capacityAmount.setVisible(true);
		okButton.setVisible(true);
	}
	@FXML
	void handheldsBackButton(ActionEvent event) {
		App.setPb(pb);
		if (permission == 1){//Employee
			try {

				App.setRoot("EmployeeHome.fxml");//load edit movie screen
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		else if (permission == 3 ){//Employee
			try {

				App.setRoot("CinemaManagerHome.fxml");//load edit movie screen
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}else if ( permission == 4){//Employee
			try {

				App.setRoot("GeneralManagerHome.fxml");//load edit movie screen
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	@FXML
	void setCapacityAmount(ActionEvent event) {

		int num = Integer.parseInt(capacityAmount.getText()) ;
		pb.setY(num);
		App.setPb(pb);
		capacityText.setText("Current Capacity is: "+pb.getY());
		editButton.setVisible(true);
		capacityAmount.setVisible(false);
		okButton.setVisible(false);

	}

	@FXML
	void setPurpleBadge(ActionEvent event) {
		pb.setStatus(!pb.getStatus());
		App.setPb(pb);
		if(pb.getStatus()) {
			capacityText.setVisible(true);
			capacityText.setText("Current Capacity is: "+pb.getY());
			editButton.setVisible(true);
			toggleSwitch.setStyle("-fx-background-color: mediumpurple;");
			fromDate.setVisible(true);
			FromText.setVisible(true);
			toDate.setVisible(true);
			toText.setVisible(true);
		}
		else {
			pb.CoronaFree();
			fromDate.setVisible(false);
			FromText.setVisible(false);
			toDate.setVisible(false);
			toText.setVisible(false);
			capacityText.setVisible(false);
			editButton.setVisible(false);
			capacityAmount.setVisible(false);
			okButton.setVisible(false);
			toggleSwitch.setStyle("-fx-background-color: lightgreen;");
		}
	}
	@FXML
	void setFromDate(ActionEvent event) {
		System.out.println(toDate.getValue());
		if (toDate.getValue() != null) {
			pb.setClosingDates(new GregorianCalendar(fromDate.getValue().getYear(),fromDate.getValue().getMonthValue(),fromDate.getValue().getDayOfMonth()), new GregorianCalendar(toDate.getValue().getYear(),toDate.getValue().getMonthValue(),toDate.getValue().getDayOfMonth()));
		}
	}

	@FXML
	void setToDate(ActionEvent event) {
		if (fromDate.getValue() != null) {
			pb.setClosingDates(new GregorianCalendar(fromDate.getValue().getYear(),fromDate.getValue().getMonthValue(),fromDate.getValue().getDayOfMonth()), new GregorianCalendar(toDate.getValue().getYear(),toDate.getValue().getMonthValue(),toDate.getValue().getDayOfMonth()));
		}
		System.out.println(pb.getClosingDates().toString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		toggleSwitch.setSelected(pb.getStatus());
		if(pb.getStatus()) {
			capacityText.setVisible(true);
			capacityText.setText("Current Capacity is: "+pb.getY());
			editButton.setVisible(true);
			capacityAmount.setVisible(false);
			okButton.setVisible(false);
			fromDate.setVisible(true);
			FromText.setVisible(true);
			toDate.setVisible(true);
			toText.setVisible(true);
			
			toggleSwitch.setStyle("-fx-background-color: mediumpurple;");
		}
		else {
			pb.CoronaFree();
			capacityText.setVisible(false);
			editButton.setVisible(false);
			capacityAmount.setVisible(false);
			okButton.setVisible(false);
			fromDate.setVisible(false);
			FromText.setVisible(false);
			toDate.setVisible(false);
			toText.setVisible(false);
			toggleSwitch.setStyle("-fx-background-color: lightgreen;");
		}

	}
}
