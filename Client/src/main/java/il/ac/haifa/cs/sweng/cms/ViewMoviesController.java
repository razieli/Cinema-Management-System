/**
 * Sample Skeleton for 'ViewMovies.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

public class ViewMoviesController implements Initializable {

    Scene scene;
    ArrayList<File> fileList = new ArrayList<File>();
    List<Movie> movies= new ArrayList<Movie>();
    static List<Cinema>cinemas=new ArrayList<Cinema>();
    private Cinema pickedCinema = null;
    private GregorianCalendar pickedDate = null;

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

    @FXML // fx:id="pickedFilter"
    private Pane pickedFilter; // Value injected by FXMLLoader


    @FXML // fx:id="addButtonAnchor"
    private AnchorPane addButtonAnchor; // Value injected by FXMLLoader


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
            App.getOcsfClient(this).getListOfCinemas();

            /*add an add button for authorized employees */
            if ( permission == 2 || permission == 3 || permission == 4) { //Manager
                Button addMovieButton = new Button("+");
                addMovieButton.setPrefSize(30,30);

                addMovieButton.setStyle("-fx-background-color: orange; -fx-font-size: 14; -fx-font-size: 14; ");
                addButtonAnchor.getChildren().add(addMovieButton);


                addMovieButton.setOnAction(e -> {
                    try {
                        // todo: what screen the press lead to
                        // storing the selected film to customise the newly created scene
                        pickedDate=null;//reset pickedDate if go to another screen
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

//            while(cinemas.isEmpty()) { Thread.yield(); }
//            for (Cinema cinema : cinemas) {
//
//                addImage(cinema);
//            }
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
                   pickedDate=null;//reset pickedDate if go to another screen
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
                       pickedDate=null;//reset pickedDate if go to another screen
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
                pickedCinema=null; //reset pickedCinema if go to another screen
                pickedDate=null;//reset pickedDate if go to another screen
                App.setRoot("CustomerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        else if (permission == 1 || permission == 2 || permission == 3 || permission == 4){//manager
            try {
                pickedDate=null;//reset pickedDate if go to another screen
                App.setRoot("EmployeeHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void handelCinemaPicked(ActionEvent event) {
        ComboBox<Cinema> cinemaFilter= new ComboBox<Cinema>();
        cinemaFilter.getItems().add(new Cinema("All",null,null));
        for(Cinema cinema : cinemas){
            cinemaFilter.getItems().add(cinema);
        }
        cinemaFilter.setPromptText("Cinema");

        cinemaFilter.setOnAction(e ->{
            pickedCinema = cinemaFilter.getValue();



            //todo: load movies from this cinema only
        });

        pickedFilter.getChildren().clear();
        pickedFilter.getChildren().add(cinemaFilter);
    }

    @FXML
    void handelDatePicked(ActionEvent event) {
        DatePicker dateFilter = new DatePicker();
        dateFilter.setPromptText("Date");

        dateFilter.setOnAction(e ->{
            LocalDate date = dateFilter.getValue();

            if(date!=null){
            pickedDate = new GregorianCalendar(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth(), 23,59,59);

            //set a conformation alert
            if (pickedDate.getTime().before(GregorianCalendar.getInstance().getTime())) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle(null);
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("This date have passed please pick another date.");
                errorAlert.showAndWait();
                if (errorAlert.getResult() == ButtonType.OK) {
                    dateFilter.setValue(null);
                    pickedDate = null;
                }

            }
            else {
                //todo: load movies from this cinema only
            }
            }
        });


        pickedFilter.getChildren().clear();
        pickedFilter.getChildren().add(dateFilter);
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

    public static List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

}
