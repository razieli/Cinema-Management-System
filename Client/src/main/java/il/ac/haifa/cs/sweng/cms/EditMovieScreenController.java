/**
 * Sample Skeleton for 'EditMovieScreen.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.entities.Theater;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

public class EditMovieScreenController implements Initializable  {

    private static Movie movie =null;
    private  ArrayList<Screening> screeningList= new ArrayList<Screening>();
    private String date ="";
    private String hour="";
    URI backButtonUri = null;

    @FXML // fx:id="titleText"
    private Text titleText; // Value injected by FXMLLoader

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

    @FXML // fx:id="hourComboBox"
    private ComboBox<LocalTime> hourComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="theaterChoiceBox"
    private ChoiceBox<Theater> theaterChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="addScreeningButton"
    private Button addScreeningButton; // Value injected by FXMLLoader

    @FXML // fx:id="inputScreening"
    private Text inputScreening; // Value injected by FXMLLoader

    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    public static Movie getSelectedFilmTitle() {
        return movie;
    }

    public static void setSelectedFilmTitle(Movie selectedFilmTitle) {
        EditMovieScreenController.movie = selectedFilmTitle;
    }


    /**
     * back button functionality
     */
    @FXML
    void handheldsBackButton(ActionEvent event) {
        try {
            movie=null;//clean the pick
            App.setRoot("ViewMovies.fxml"); //set the scean to the last page.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * delete button functionality
     */
    @FXML
    void handheldsMovieDelete(ActionEvent event) {
        //set a warning alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)//delete operation from database
        {
            // TODO: call function to delete the movie
        }
    }

    /**
     * update state button functionality
     */
    @FXML
    void handheldsMovieUpdate(ActionEvent event) throws URISyntaxException {
        if(!englishTitle.getText().isEmpty() && !hebrewTitle.getText().isEmpty() && !descriptionBox.getText().isEmpty() && !yearBox.getText().isEmpty() && !lengthBox.getText().isEmpty() && !PGRaitingBox.getText().isEmpty() && !castBox.getText().isEmpty() && !posterBox.getText().isEmpty() && !trailerBox.getText().isEmpty()) {
            //set a conformation alert
            Alert confarmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confarmationAlert.setTitle(null);
            confarmationAlert.setHeaderText(null);
            confarmationAlert.setContentText("Are You Sure?");
            confarmationAlert.showAndWait();
            if (confarmationAlert.getResult() == ButtonType.OK) {
                if(movie == null){//in a case of adding a new movie
                    movie =new Movie();
                }

                //update the current movie state
                movie.setEngName(englishTitle.getText());
                movie.setHebName(hebrewTitle.getText());
                movie.setDescription(descriptionBox.getText());
                movie.setYear(Integer.valueOf(yearBox.getText()));
                movie.setLength(Integer.valueOf(lengthBox.getText()));
                movie.setAgeRestriction(Integer.valueOf(PGRaitingBox.getText()));
                movie.setCastList(castBox.getText());
                movie.setPosterUrl(new URI(posterBox.getText()));
                movie.setTrailerUrl(new URI(trailerBox.getText()));
                movie.setScreening(screeningList);

                //update move on database
                // TODO: update entire movie data.
                App.getOcsfClient(this).updateScreenings(screeningList);

                updateScreen();
            }
        }

        else{
            //set a conformation alert
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(null);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("One or more sections is empty. Please make sure to fill everything.");
            errorAlert.showAndWait();
            if (errorAlert.getResult() == ButtonType.OK);
        }

    }

    /**
     * method that update the scene components
     */
    public void updateScreen(){

        /*
        *set components size to adapt window size
        */
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


//        try {
//            backButtonUri = new URI("https://cdn.pixabay.com/photo/2016/09/05/10/50/app-1646213_640.png");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        ImageView backButtonIm = new ImageView(backButtonUri.toString());
//        backButtonIm.setPreserveRatio(true);
//        backButtonIm.setFitHeight(backButton.getPrefHeight());
//        backButtonIm.setFitWidth(backButton.getPrefWidth());
//        backButton.setGraphic(backButtonIm);

        if (movie!=null) { //in case of adding a new movie
            titleText.setText("Update Movie");

            /*
             *set loaded text from the entity in the scene components
             */
            //set title to show on screen
            englishTitle.setText(movie.getEngName());
            inputEngTitle.setText(movie.getEngName());
            hebrewTitle.setText(movie.getHebName());
            inputHebTitle.setText(movie.getHebName());

            //set poster to show from url
            posterBox.setText(movie.getPosterUrl().toString());
            trailerBox.setText(movie.getTrailerUrl().toString());
            inputImage.setImage(new Image(movie.getPosterUrl().toString()));
            inputTrailer.setOnAction(new EventHandler<ActionEvent>() {
                                         @Override
                                         public void handle(ActionEvent e) {
                                             try {
                                                 Desktop.getDesktop().browse(movie.getTrailerUrl());
                                             } catch (IOException e1) {
                                                 e1.printStackTrace();
                                             }
                                         }
                                     }
            );

            //set year to show
            yearBox.setText(String.valueOf(movie.getYear()));
            inputYear.setText(yearBox.getText());

            //set movie length to show on screen
            lengthBox.setText(String.valueOf(movie.getLength()));
            inputLength.setText(String.valueOf(movie.getLength()) + " min");

            //set cast list to show on screen
            castBox.setText(movie.getCastList());
            inputCast.setText(movie.getCastList());

            //set PG rating to show on screen
            PGRaitingBox.setText(String.valueOf(movie.getAgeRestriction()));
            inputPGRaiting.setText(String.valueOf(movie.getAgeRestriction()));

            //set description to show on screen
            descriptionBox.setText(movie.getDescription());
            inputDescription.setText(movie.getDescription());

            //set movie screening time to show on screen
            for (Screening screening : movie.getScreening()) {
                addScreening(screening);
            }

            String displayScreening = "";
            SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd E HH:mm; "); //set a date format
            for (Screening screening : movie.getScreening()) {
                displayScreening += format.format(screening.getDate().getTime()).toString();
            }
            inputScreening.setText(displayScreening);
        }
    }

    /**
     * method that initialize the scene after everything has loaded
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateScreen(); //call to refresh screen component

        /*set Combobox*/
        //set hour Combo box to be able to add new value
        hourComboBox.setEditable(true);

        /*set Combobox options*/
        //hour
        for(int i=0;i<=23;i++) {
            for (int j = 0; j < 60; j=j+15) {
                hourComboBox.getItems().add(LocalTime.of(i,j,0,0));
            }
        }

        /*on action listener for comboBox*/
        //date
        datePicker.setOnAction((event) -> {
            date = String.valueOf(datePicker.getValue());
        });

        //hour
        hourComboBox.setOnAction((event) -> {
            hour = String.valueOf(hourComboBox.getValue());
        });
    }

    /**
     * add new screening button
     */
    @FXML
    void handheldsAddScreeningButton(ActionEvent event) {
        if(date.isEmpty() ||  hour.isEmpty()) {
            //set a conformation alert
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(null);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("One or more sections is empty. Please make sure to fill the date and time.");
            errorAlert.showAndWait();

        }

        else{
            //add to screeningList
            String[] dateSplit = date.split("-", 3);//split the date string to year,mount and day
            String[] hourMin = hour.split(":", 2);//split the date string to hour and minuets
            GregorianCalendar gregorianCalendar = new GregorianCalendar(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[2]), Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]));


            if (gregorianCalendar.getTime().before(GregorianCalendar.getInstance().getTime())) {
                //set a conformation alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle(null);
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("This date have passed please pick another date.");
                errorAlert.showAndWait();

            }
            else {
                datePicker.setValue(null);
                hourComboBox.setValue(null);
                date = "";
                hour = "";
                addScreening(gregorianCalendar);
            }
        }


    }

    /**
     * method that sat up new screening button and add new screening to screeningList
     * @param gregorianCalendar - GregorianCalendar value that for the wanted date
     */
    protected void addScreening(GregorianCalendar gregorianCalendar){

            //add to screeningList
            Screening screening = new Screening(movie, null, gregorianCalendar); //create new screening object

            screeningList.add(screening); //add the screening object to the screeningList

            /*add a button*/
            //set a date format
            SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd E HH:mm");
            String name = format.format(screening.getDate().getTime()).toString();

            //add button to screen
            Button screeningButton = new Button(name);
            screeningFlow.getChildren().add(screeningButton);
            FlowPane.setMargin(screeningButton, new Insets(3, 3, 3, 3));

            //button functionality
            screeningButton.setOnAction(e -> {
                //remove the screening button and screening apprentice in screenList
                    screeningFlow.getChildren().remove(screeningButton);
                    screeningList.remove(screening);
            });
    }

    /**
     * method that sat up new screening button and add new screening to screeningList
     * @param screening - Screening value
     */
    protected void addScreening(Screening screening){
        //add to screeningList
        if(!screeningList.contains(screening)) {
            screeningList.add(screening);

            /*add a button*/
            //set a date format
            SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd E HH:mm");
            String name = format.format(screening.getDate().getTime()).toString();

            //add button to screen
            Button screeningButton = new Button(name);
            screeningFlow.getChildren().add(screeningButton);
            FlowPane.setMargin(screeningButton, new Insets(3, 3, 3, 3));

            //button functionality
            screeningButton.setOnAction(e -> {
                //remove the screening button and screening apprentice in screenList
                    screeningList.remove(screening);
                    screeningFlow.getChildren().remove(screeningButton);
            });
        }
    }
}
