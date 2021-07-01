package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
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
 * Controller for price change submission.
 */
public class PriceChangeSubmissionController implements Initializable {

    private static final double MAX_PRICE = 200;

    private List<PriceChange> priceChanges;

    @FXML
    private ListView<PriceChange> priceChangeListView;

    @FXML
    private Text movie;

    @FXML
    private Text status;

    private List<Movie> movieList;

    @FXML
    private ListView<Movie> movieListView;

    @FXML
    private Text movieSubmit;

    @FXML
    private Text oldPrice;

    @FXML
    private TextField newPrice;

    /**
     * Sends the price change the user entered to the server.
     * Shows an error if any of the fields did not pass verification.
     */
    @FXML
    protected void addPriceChange() {
        if (verifyInput()) {
            PriceChange priceChange = new PriceChange((Employee) App.getUser(), movieListView.getSelectionModel().getSelectedItem(), Double.parseDouble(newPrice.getText()));
            App.getOcsfClient(this).submitPriceChange(priceChange);
            priceChanges.add(priceChange);
        }
    }

    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("ContentManagerHome.fxml"); //set the scean to the last page.
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
     * Verifies the price change data the user has entered.
     *
     * @return True if data is verified, false otherwise.
     */
    private boolean verifyInput() {
        if (movieListView.getSelectionModel().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Price change submission failed:", "Please select a movie.");
            return false;
        }

        if (newPrice.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Price change submission failed:", "Please enter a new price.");
            return false;
        }

        double parsedNewPrice;
        try {
            parsedNewPrice = Double.parseDouble(newPrice.getText());
        } catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Price change submission failed:", "Please enter a valid price.");
            return false;
        }

        if (parsedNewPrice < 0 || parsedNewPrice > MAX_PRICE) {
            showAlert(Alert.AlertType.ERROR, "Price change submission failed:", "Please enter a price between 0 and " + MAX_PRICE + ".");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.getOcsfClient(this).getListOfPriceChanges(App.getUser());
        App.getOcsfClient(this).getListOfMovies();
        while (priceChanges == null || movieList == null) {
            Thread.yield();
        }
        updatePriceChangeList();
        updateMovieList();
        priceChangeListView.getSelectionModel().selectedItemProperty().addListener((observableValue, priceChange, t1) -> updateSelectedPriceChange(t1));
        movieListView.getSelectionModel().selectedItemProperty().addListener((observableValue, prevSelected, t1) -> updateSelectedMovie(t1));
    }

    public void updatePriceChangeList() {
        priceChanges.removeIf(priceChange -> !priceChange.getSubmitter().getUserName().equals(App.getUser().getUserName()));
        this.priceChangeListView.getItems().addAll(priceChanges);
    }

    public void updateMovieList() {
        this.movieListView.getItems().addAll(movieList);
    }

    private void updateSelectedPriceChange(PriceChange priceChange) {
        this.movie.setText(priceChange.getMovie().toString());
        PriceChange.Status status = priceChange.getStatus();
        switch (status) {
            case SUBMITTED -> this.status.setText("Waiting");
            case ACCEPTED -> this.status.setText("Accepted");
            case REJECTED -> this.status.setText("Rejected");
        }
    }

    private void updateSelectedMovie(Movie movie) {
        this.movieSubmit.setText(movie.toString());
        this.oldPrice.setText(String.valueOf(movie.getPrice()));
    }

    public void setPriceChanges(List<PriceChange> priceChanges) {
        this.priceChanges = priceChanges;
    }

    public void setMovies(List<Movie> movies) { this.movieList = movies; }

    public void handlePriceChangeSubmissionResponse() {
        Platform.runLater(() -> this.priceChangeListView.getItems().add(priceChanges.get(priceChanges.size() - 1)));
        this.newPrice.setText("");
        Platform.runLater(() -> showAlert(Alert.AlertType.INFORMATION, "Price change submitted successfully.", ""));
    }
}
