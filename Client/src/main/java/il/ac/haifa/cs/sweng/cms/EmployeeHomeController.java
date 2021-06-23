package il.ac.haifa.cs.sweng.cms;
import il.ac.haifa.cs.sweng.cms.App;
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

public class EmployeeHomeController  implements Initializable {

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		helloUserName.setText("Hello " + App.getUserName() + " !");
	}

	@FXML
	private Button LogOutBtn;

	@FXML
	private Button movieListBtn;

	@FXML
	private Button movieAddBtn;

	@FXML
	private Button purpleBtn;

	@FXML
	private Button ComplaintHandlingBtn;

	@FXML
	private Button operationalBtn;

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
	void switchToComplaintHandlingScreen(ActionEvent event) {
		try {
			App.setRoot("ComplaintHandling.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void switchToMovieAddScreen(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("New features coming soon..  :)");
		alert.showAndWait();
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
	void switchToOperationalMonitoringScreen(ActionEvent event) {
		if (App.getUserPermission() < 3 )
		{
			showAlert(Alert.AlertType.ERROR, null, "No Permission!");
		}
		else {
			try {
				App.setRoot("OperationalReports.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void switchToPurpleBadgeScreen(ActionEvent event) {
		//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		//        alert.setTitle(null);
		//        alert.setHeaderText(null);
		//        alert.setContentText("New features coming soon..  :)");
		//        alert.showAndWait();
		try {
			App.setRoot("PurpleBadge.fxml");
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


}
