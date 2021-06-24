package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.PriceChange;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for price changes handling.
 */
public class PriceChangeHandlingController implements Initializable {

    @FXML
    private ListView<PriceChange> priceChangesList;

    @FXML
    private Text movie;

    @FXML
    private Text status;

    @FXML
    private Label oldPrice;

    @FXML
    private Label newPrice;

    private List<PriceChange> priceChanges;

    /**
     * Accepts the price change and sends reply to the server.
     */
    @FXML
    protected void onAccept(ActionEvent event) {
        PriceChange priceChange = priceChangesList.getSelectionModel().getSelectedItem();
        priceChange.accept();
        App.getOcsfClient(this).replyToPriceChange(priceChange);
    }

    /**
     * Rejects the price change and sends reply to the server.
     */
    @FXML
    protected void onReject(ActionEvent event) {
        PriceChange priceChange = priceChangesList.getSelectionModel().getSelectedItem();
        priceChange.reject();
        App.getOcsfClient(this).replyToPriceChange(priceChange);
    }

    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("GeneralManagerHome.fxml"); //set the screen to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        App.getOcsfClient(this).getListOfPriceChanges(null);
        while(priceChanges == null) {
            Thread.yield();
        }
        priceChanges.removeIf(priceChange -> priceChange.getStatus() != PriceChange.Status.SUBMITTED);
        updatePriceChangesList();
        priceChangesList.getSelectionModel().selectedItemProperty().addListener((observableValue, priceChange, t1) -> updateSelectedPriceChange(t1));
    }

    private void updateSelectedPriceChange(PriceChange selected) {
        this.movie.setText(selected.getMovie().getEngName());
        PriceChange.Status status = selected.getStatus();
        switch (status) {
            case SUBMITTED -> this.status.setText("Waiting");
            case ACCEPTED -> this.status.setText("Accepted");
            case REJECTED -> this.status.setText("Rejected");
        }
        this.oldPrice.setText(String.valueOf(selected.getOrigPrice()));
        this.newPrice.setText(String.valueOf(selected.getNewPrice()));
    }

    private void updatePriceChangesList() {
        this.priceChangesList.getItems().addAll(priceChanges);
    }

    public void setPriceChanges(List<PriceChange> priceChanges) {
        this.priceChanges = priceChanges;
    }

    public void onReplyReceived() {
        PriceChange selected = this.priceChangesList.getSelectionModel().getSelectedItem();
        updateSelectedPriceChange(selected);
    }
}
