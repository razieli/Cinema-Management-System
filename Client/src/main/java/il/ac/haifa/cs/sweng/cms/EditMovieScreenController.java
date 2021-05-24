/**
 * Sample Skeleton for 'EditMovieScreen.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.awt.Desktop;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditMovieScreenController implements Initializable  {

    private static Movie movie;


    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="posterBox"
    private TextArea posterBox; // Value injected by FXMLLoader

    @FXML // fx:id="trailerBox"
    private TextArea trailerBox; // Value injected by FXMLLoader

    @FXML // fx:id="englishTitle"
    private TextField englishTitle; // Value injected by FXMLLoader

    @FXML // fx:id="hebrewTitle"
    private TextField hebrewTitle; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionBox"
    private TextArea descriptionBox; // Value injected by FXMLLoader

    @FXML // fx:id="yearBox"
    private TextField yearBox; // Value injected by FXMLLoader

    @FXML // fx:id="lengthBox"
    private TextField  lengthBox; // Value injected by FXMLLoader

    @FXML // fx:id="PGRaitingBox"
    private TextField PGRaitingBox; // Value injected by FXMLLoader

    @FXML // fx:id="availableBox"
    private TextField availableBox; // Value injected by FXMLLoader

    @FXML // fx:id="castBox"
    private TextArea castBox; // Value injected by FXMLLoader

    @FXML // fx:id="directorBox"
    private TextField directorBox; // Value injected by FXMLLoader

    @FXML // fx:id="screeningFlow"
    private FlowPane screeningFlow; // Value injected by FXMLLoader

    @FXML // fx:id="deleteLastButton"
    private Button deleteLastButton; // Value injected by FXMLLoader

    @FXML // fx:id="saveChangesButton"
    private Button saveChangesButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="inputEngTitle"
    private Text inputEngTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputHebTitle"
    private Text inputHebTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputImage"
    private ImageView inputImage; // Value injected by FXMLLoader

    @FXML // fx:id="inputTrailer"
    private Hyperlink inputTrailer; // Value injected by FXMLLoader

    @FXML // fx:id="inputLength"
    private Text inputLength; // Value injected by FXMLLoader

    @FXML // fx:id="inputYear"
    private Text inputYear; // Value injected by FXMLLoader

    @FXML // fx:id="inputPGRaiting"
    private Text inputPGRaiting; // Value injected by FXMLLoader

    @FXML // fx:id="inputDirector"
    private Text inputDirector; // Value injected by FXMLLoader

    @FXML // fx:id="inputCast"
    private Text inputCast; // Value injected by FXMLLoader

    @FXML // fx:id="inputDescription"
    private Text inputDescription; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPaneLeft"
    private ScrollPane scrollPaneLeft; // Value injected by FXMLLoader

    @FXML // fx:id="anchorPaneLeft"
    private AnchorPane anchorPaneLeft; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPaneRight"
    private ScrollPane scrollPaneRight; // Value injected by FXMLLoader

    @FXML // fx:id="anchorPaneRight"
    private AnchorPane anchorPaneRight; // Value injected by FXMLLoader


    public static Movie getSelectedFilmTitle() {
        return movie;
    }

    public static void setSelectedFilmTitle(Movie selectedFilmTitle) {
        EditMovieScreenController.movie = selectedFilmTitle;
    }

    @FXML
    void handheldsAviabilety(InputMethodEvent event) {

    }

    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            App.setRoot("ManagerViewMovies.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handheldsMovieDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES)
        {
            // TODO: call function to delete the movie
        }
    }

    @FXML
    void handheldsMovieUpdate(ActionEvent event) throws URISyntaxException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK)
        {
            movie.setEngName(englishTitle.getText());
            movie.setHebName(hebrewTitle.getText());
            movie.setDescription(descriptionBox.getText());
            movie.setYear(Integer.valueOf(yearBox.getText()));
            movie.setLength(Integer.valueOf(lengthBox.getText()));
            movie.setAgeRestriction(Integer.valueOf(PGRaitingBox.getText()));
            movie.setCastList(castBox.getText());
            movie.setPosterUrl(new URI(posterBox.getText()));
            movie.setTrailerUrl(new URI(trailerBox.getText()));
            // TODO: need to update screenings
            updateScreen();

        }
    }

    @FXML
    void handheldsCast(InputMethodEvent event) {

    }

    @FXML
    void handheldsDeleteButton(ActionEvent event) {

    }

    @FXML
    void handheldsDeleteLastButton(ActionEvent event) {

    }

    @FXML
    void handheldsDescription(InputMethodEvent event) {

    }

    @FXML
    void handheldsDirector(InputMethodEvent event) {

    }

    @FXML
    void handheldsEnglishName(InputMethodEvent event) {

    }

    @FXML
    void handheldsHeberwName(InputMethodEvent event) {

    }

    @FXML
    void handheldsPosterURL(InputMethodEvent event) {

    }

    @FXML
    void handheldsSaveChangesButton(ActionEvent event) {

    }

    @FXML
    void handheldsScreeningChack(ActionEvent event) {

    }

    @FXML
    void handheldsTrailerURL(InputMethodEvent event) {

    }

    @FXML
    void handheldsYear(InputMethodEvent event) {

    }

    @FXML
    void handheldsPGRaiting(InputMethodEvent event) {

    }

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    public TextArea getPosterBox() {
        return posterBox;
    }

    public void setPosterBox(TextArea posterBox) {
        this.posterBox = posterBox;
    }

    public TextArea getTrailerBox() {
        return trailerBox;
    }

    public void setTrailerBox(TextArea trailerBox) {
        this.trailerBox = trailerBox;
    }

    public TextField getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(TextField englishTitle) {
        this.englishTitle = englishTitle;
    }

    public TextField getHebrewTitle() {
        return hebrewTitle;
    }

    public void setHebrewTitle(TextField hebrewTitle) {
        this.hebrewTitle = hebrewTitle;
    }

    public TextArea getDescriptionBox() {
        return descriptionBox;
    }

    public void setDescriptionBox(TextArea descriptionBox) {
        this.descriptionBox = descriptionBox;
    }

    public TextField getYearBox() {
        return yearBox;
    }

    public void setYearBox(TextField yearBox) {
        this.yearBox = yearBox;
    }

    public TextField getAvailableBox() {
        return availableBox;
    }

    public void setAvailableBox(TextField availableBox) {
        this.availableBox = availableBox;
    }

    public TextArea getCastBox() {
        return castBox;
    }

    public void setCastBox(TextArea castBox) {
        this.castBox = castBox;
    }

    public TextField getDirectorBox() {
        return directorBox;
    }

    public void setDirectorBox(TextField directorBox) {
        this.directorBox = directorBox;
    }



    public void updateScreen(){

        scrollPaneRight.widthProperty().addListener((obs, oldVal, newVal) -> {
            anchorPaneRight.prefWidthProperty().bind(scrollPaneRight.widthProperty());
        });

        scrollPaneRight.heightProperty().addListener((obs, oldVal, newVal) -> {
            anchorPaneRight.prefHeightProperty().bind(scrollPaneRight.heightProperty());
        });

        scrollPaneLeft.widthProperty().addListener((obs, oldVal, newVal) -> {
            anchorPaneLeft.prefWidthProperty().bind(scrollPaneLeft.widthProperty());
        });

        scrollPaneLeft.heightProperty().addListener((obs, oldVal, newVal) -> {
            anchorPaneLeft.prefHeightProperty().bind(scrollPaneLeft.heightProperty());
        });

        URI backButtonUri = null;
        try {
            backButtonUri = new URI("https://cdn.pixabay.com/photo/2016/09/05/10/50/app-1646213_640.png");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ImageView backButtonIm = new ImageView(backButtonUri.toString());
        backButtonIm.setPreserveRatio(true);
        backButtonIm.setFitHeight(backButton.getPrefHeight());
        backButtonIm.setFitWidth(backButton.getPrefWidth());
        backButton.setGraphic(backButtonIm);

        posterBox.setText(movie.getPosterUrl().toString());
        trailerBox.setText(movie.getTrailerUrl().toString());
        inputImage.setImage(new Image(movie.getPosterUrl().toString()));
        inputTrailer.setOnAction(new EventHandler<ActionEvent>() {
                 @Override public void handle(ActionEvent e) {
                     try {
                         Desktop.getDesktop().browse(movie.getTrailerUrl());
                     } catch (IOException e1) {
                         e1.printStackTrace();
                     }
                 }
             }
        );
        englishTitle.setText(movie.getEngName());
        inputEngTitle.setText(movie.getEngName());

        hebrewTitle.setText(movie.getHebName());
        inputHebTitle.setText(movie.getHebName());

        yearBox.setText(String.valueOf(movie.getYear()));
        inputYear.setText(yearBox.getText());

        lengthBox.setText(String.valueOf(movie.getLength()));
        inputLength.setText(String.valueOf(movie.getLength()) + "min");

        castBox.setText(movie.getCastList());
        inputCast.setText(movie.getCastList());

        PGRaitingBox.setText(String.valueOf(movie.getAgeRestriction()));
        inputPGRaiting.setText(String.valueOf(movie.getAgeRestriction()));

        descriptionBox.setText(movie.getDescription());
        inputDescription.setText(movie.getDescription());

        List<String> screeningsList = new LinkedList<>();
        for(int i=0; i < movie.getScreening().size(); i++)
        {
            screeningsList.add(movie.getScreening().get(i).getTheater().getPlaceName());
            Date date = movie.getScreening().get(i).getDate().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String strDate = dateFormat.format(date);
            screeningsList.add(strDate);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateScreen();
    }
}
