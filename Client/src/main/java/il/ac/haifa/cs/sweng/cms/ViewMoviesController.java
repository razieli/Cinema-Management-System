/**
 * Sample Skeleton for 'ViewMovies.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewMoviesController implements Initializable {

    Scene scene;
    ArrayList<File> fileList = new ArrayList<File>();
    List<Movie> movies= new ArrayList<Movie>();
    HBox hb = new HBox();

    private int permission= App.getUserPermission();

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="flow"
    private FlowPane flow; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="filterMenu"
    private MenuButton filterMenu; // Value injected by FXMLLoader

    @FXML // fx:id="addButtonAnchor"
    private AnchorPane addButtonAnchor; // Value injected by FXMLLoader

//    @FXML
//    private Button addMovieButton;

    //components and items to be added
    @FXML
    ImageView pic;
    @FXML
    Image image;
    @FXML
    String id;

    /**
     * method that initialize the scene after everything has loaded
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
/* Gets list of movies from the server*/
            App.getOcsfClient(this).getListOfMovies();

/*set up buttons*/
            URI searchButtonUri = new URI("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbws1761LoKcQ68sQeqmJNXr2WhuEE5SEVY1DmKK6mka1o8FpbeEmTj_cBWfRR1ksDbAc&usqp=CAU");
            ImageView searchButtonIm = new ImageView(searchButtonUri.toString());
            searchButtonIm.setPreserveRatio(true);
            searchButtonIm.setFitHeight(searchButton.getPrefHeight());
            searchButtonIm.setFitWidth(searchButton.getPrefWidth());
            searchButton.setGraphic(searchButtonIm);

//            URI backButtonUri = new URI("https://cdn.pixabay.com/photo/2016/09/05/10/50/app-1646213_640.png");
//            ImageView backButtonIm = new ImageView(backButtonUri.toString());
//            backButtonIm.setPreserveRatio(true);
//            backButtonIm.setFitHeight(backButton.getPrefHeight());
//            backButtonIm.setFitWidth(backButton.getPrefWidth());
//            backButton.setGraphic(backButtonIm);

            if ( permission == 2 || permission == 3 || permission == 4) { //Manager
                Button addMovieButton = new Button("+");
                addMovieButton.setPrefSize(30,30);

                addMovieButton.setStyle("-fx-background-color: orange; -fx-font-size: 14; -fx-font-size: 15; ");
                addButtonAnchor.getChildren().add(addMovieButton);


                addMovieButton.setOnAction(e -> {
                    try {
                        // todo: what screen the press lead to
                        // storing the selected film to customise the newly created scene
                        App.setRoot("EditMovieScreen.fxml");//load edit movie screen
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

            }

            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);//remove the bottom bar

            /*set components size to adapt window size*/
            scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                flow.prefWidthProperty().bind(scrollPane.widthProperty());
            });

            scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                flow.prefHeightProperty().bind(scrollPane.heightProperty());
            });

            /*load movie component*/
            while(movies.isEmpty()) { Thread.yield(); }
                        for (Movie movie : movies) {

                                    addImage(movie);
                                }
                    }

        catch(Exception e){
        e.printStackTrace();
    }

}

    /**
     * method to add new movie component to the screen
     * @param movie - movie type to be added
     */
    protected void addImage(Movie movie){

        /*add poster of the movie*/
        pic = new ImageView(movie.getPosterUrl().toString());
        pic.setFitWidth(160);
        pic.setFitHeight(220);

        /*add title of the movie*/
        Text textHebName = new Text(movie.getHebName());
        textHebName.setFill(Color.ORANGE);//format title text
        textHebName.setFont(Font.font(null, FontWeight.BOLD, 12));
        Text textEngName = new Text(movie.getEngName());
        textEngName.setFill(Color.ORANGE);//format title text
        textEngName.setFont(Font.font(null, FontWeight.BOLD, 12));

        GridPane gridPane = new GridPane();

        /*add screening times*/
        SimpleDateFormat format= new SimpleDateFormat ("YY.MM.dd E HH:mm; ");//set a date format
        String screenTime="";
        int i=0,j=0;
        for(Screening screen:movie.getScreening()){
            screenTime= format.format(screen.getDate().getTime()).toString() ;
            Text textScreenTime = new Text(screenTime);
            textScreenTime.setFill(Color.ORANGE);
            gridPane.add(textScreenTime,i,j);//add screening time to grid

            //set location in the grid
            i++;
            if (i % 2 == 0) {
                j++;
                i=0;
            }

        }


        VBox vb = new VBox(4, pic, textHebName, textEngName, gridPane); //create new VBox component to hold all the movie data

        /*add new component to the scene*/
        flow.getChildren().add(vb);
        FlowPane.setMargin(vb, new Insets(5,30,5,10));

/*on action functionality, go into edit screen of the chosen movie*/
        vb.setOnMouseClicked(e -> {

           if (permission == 2 || permission == 3 || permission == 4) { //Manager
               try {
                   // todo: what screen the press lead to
                   // storing the selected film to customise the newly created scene
                EditMovieScreenController.setSelectedFilmTitle(movie);//pass the movie to  the next screen
                App.setRoot("EditMovieScreen.fxml");//load edit movie screen
               } catch (IOException ex) {
                   ex.printStackTrace();
               }
           }

           else if (permission == 0 || permission == 1) { //Costumer
               if (permission == 1){
                   Alert alert = new Alert(Alert.AlertType.WARNING);
                   alert.setTitle(null);
                   alert.setHeaderText(null);
                   alert.setContentText("You do not have the required permissions.");
                   alert.showAndWait();
               }

               else{
                   try {
                       // todo: what screen the press lead to
                       // storing the selected film to customise the newly created scene
                       MovieOverviewController.setMovie(movie);
                       App.setRoot("MovieOverview.fxml");//load edit movie screen
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
               }
           }
           });


    }

    /**
     * back button functionality
     */
    @FXML
    void handheldsBackButton(ActionEvent event) {
        if (permission == 0){//customer
            try {
                // todo: what screen the press lead to
                App.setRoot("CustomerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        else if (permission == 1 || permission == 2 || permission == 3 || permission == 4){//manager
            try {
                App.setRoot("EmployeeHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * filter menu functionality
     */
    @FXML
    void handheldsFilterMenu(ActionEvent event) {
        //set an information alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("New features coming soon..  :)");
        alert.showAndWait();
    }

    /**
     * search bar functionality
     */
    @FXML
    void handheldsSearchButton(ActionEvent event) {
        //set an information alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("New features coming soon..  :)");
        alert.showAndWait();
    }

    /**
     * @return given id ()
     */
    @FXML
    public String getId () {
        return id;
    }

    /**
     * set movie list
     * @param movies List
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}