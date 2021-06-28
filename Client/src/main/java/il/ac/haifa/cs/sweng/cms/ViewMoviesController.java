/**
 * Sample Skeleton for 'ViewMovies.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.entities.Theater;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.*;

public class ViewMoviesController implements Initializable {

//    Scene scene;
    ArrayList<File> fileList = new ArrayList<File>();
    List<Movie> allMovies= new ArrayList<Movie>();
    List<Movie> comingSoonMovies= new ArrayList<Movie>();
    List<Movie> movies= new ArrayList<Movie>();
    static List<Cinema>cinemas=new ArrayList<Cinema>();
    private Cinema pickedCinema = null;
    private GregorianCalendar pickedDate = (GregorianCalendar)GregorianCalendar.getInstance();

    private int permission= App.getUserPermission();

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="flow"
    private FlowPane flow; // Value injected by FXMLLoader

    @FXML // fx:id="pickedFilter"
    private Pane pickedFilter; // Value injected by FXMLLoader

    @FXML // fx:id="soonAnchorPane"
    private AnchorPane soonAnchorPane; // Value injected by FXMLLoader

    @FXML
    private HBox comingSoonHBox;

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

            /*init comingSoonMovies movie list*/
            for(Movie m: allMovies)
                if (m.getPremiere().getTime().after(GregorianCalendar.getInstance().getTime())){ // case of coming soon Movie) {
                        comingSoonMovies.add(m);
                        comingSoonHBox.setAlignment(Pos.CENTER);
                    }

            /*add an add button for authorized employees */
            if ( permission == 2 || permission == 3 || permission == 4) { //Manager
                Button addMovieButton = new Button("+");
                addMovieButton.setPrefSize(30,30);

                addMovieButton.setStyle("-fx-background-color: orange; -fx-font-size: 14; ");
                addButtonAnchor.getChildren().add(addMovieButton);


                addMovieButton.setOnAction(e -> {
                    try {
                        // storing the selected film to customise the newly created scene
                        pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                        App.setRoot("EditMovieScreen.fxml");//load edit movie screen
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

            }

            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);//remove the bottom bar



            /*set components size to adapt window size*/
            //coming soon
            soonAnchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                comingSoonHBox.prefWidthProperty().bind(soonAnchorPane.widthProperty());
            });

            soonAnchorPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                comingSoonHBox.prefHeightProperty().bind(soonAnchorPane.heightProperty());
            });

            //movies on theater
            scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                flow.prefWidthProperty().bind(scrollPane.widthProperty());
            });

            scrollPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                flow.prefHeightProperty().bind(scrollPane.heightProperty());
            });

//            while(allMovies.isEmpty()) { Thread.yield(); }
//            for (Movie movie : allMovies) {
//                addImage(movie);
//            }

            while(allMovies.isEmpty()) { Thread.yield(); }
            movies.addAll(allMovies);
            updateScreen();
                    }

        catch(Exception e){
        e.printStackTrace();
    }

}

    private void updateScreen (){
        comingSoonHBox.getChildren().clear();
        flow.getChildren().clear();



        /*load movie component*/
        while(cinemas.isEmpty()) { Thread.yield(); }

        for (Movie movie : movies) {
            addImage(movie);
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

        if (movie.getPremiere().after(GregorianCalendar.getInstance())){ // case of coming soon Movie
            pic.setFitWidth(100);
            pic.setFitHeight(160);

            Text textComingSoon = new Text("Coming Soon!");
            textComingSoon.setFill(Color.WHITESMOKE);
            textComingSoon.setFont(Font.font(null, FontWeight.BOLD, 12));
//            VBox vb = new VBox(4, pic, textHebName, textEngName, textComingSoon); //create new VBox component to hold all the movie data
            VBox vb = new VBox(3, textHebName, textEngName, textComingSoon); //create new VBox component to hold all the movie data
            vb.setAlignment(Pos.CENTER);

            vb.setStyle("-fx-background-color: black; -fx-opacity: 65%;");
            StackPane sp = new StackPane(pic,vb); //create new VBox component to hold all the movie data
            sp.setAlignment(Pos.BOTTOM_CENTER);
            comingSoonHBox.getChildren().add(sp);
            comingSoonHBox.setMargin(sp, new Insets(5, 30, 5, 10));

            vb.setOnMouseClicked(e -> {
                if (permission == 2 || permission == 3 || permission == 4) { //Manager
                    try {

                        // storing the selected film to customise the newly created scene
                        pickedDate = (GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                        EditMovieScreenController.setSelectedFilmTitle(movie);//pass the movie to  the next screen
                        App.setRoot("EditMovieScreen.fxml");//load edit movie screen
                    } catch (IOException ex) {

                    }
                }
            });

        }

         else{ // case of a Movie in the thearers
            GridPane gridPane = new GridPane();

            /*add screening times*/
            SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd E HH:mm; ");//set a date format
            String screenTime = "";
            int i = 0, j = 0, k=0;
            movie.getScreening().sort(new SortByDate());//sort screening list
            for (Screening screen : movie.getScreening()) {
               if((screen.getDate().after(pickedDate) && k<4) && (pickedCinema==null || screen.getTheater().getCinema().getName().equals(pickedCinema.getName()))){
                            screenTime = format.format(screen.getDate().getTime()).toString();
                            Text textScreenTime = new Text(screenTime);
                            textScreenTime.setFill(Color.ORANGE);
                            gridPane.add(textScreenTime, i, j);//add screening time to grid

                            //set location in the grid
                            i++;
                            if (i % 2 == 0) {
                                j++;
                                i = 0;
                            }
                            k++;//show only 4 screenings at a time
                    }
            }

            VBox vb = new VBox(4, pic, textHebName, textEngName, gridPane); //create new VBox component to hold all the movie data
            vb.setAlignment(Pos.CENTER);

            /*add new component to the scene*/
            flow.getChildren().add(vb);
            FlowPane.setMargin(vb, new Insets(5, 30, 5, 10));

            /*on action functionality, go into edit screen of the chosen movie*/
            vb.setOnMouseClicked(e -> {

                if (permission == 2 || permission == 3 || permission == 4) { //Manager
                    try {

                        // storing the selected film to customise the newly created scene
                        pickedDate = (GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                        EditMovieScreenController.setSelectedFilmTitle(movie);//pass the movie to  the next screen
                        App.setRoot("EditMovieScreen.fxml");//load edit movie screen
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (permission == 0 || permission == 1) { //Costumer
                    if (permission == 1) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("You do not have the required permissions.");
                        alert.showAndWait();
                    } else {
                        try {
                            // storing the selected film to customise the newly created scene
                            pickedDate = (GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                            MovieOverviewController.setMovie(movie);
                            if (pickedCinema != null)
                                MovieOverviewController.setPickedCinema(pickedCinema);
                            App.setRoot("MovieOverview.fxml");//load edit movie screen
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    /**
     * back button functionality
     */
    @FXML
    void handheldsBackButton(ActionEvent event) {
        if (permission == 0){//customer
            try {
                pickedCinema=null; //reset pickedCinema if go to another screen
                pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                App.setRoot("CustomerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        else if (permission == 1 ){//Employee
            try {
                pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                App.setRoot("EmployeeHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (permission == 2){//content manager
            try {
                pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                App.setRoot("ContentManagerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (permission == 3){//cinema manager
            try {
                pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                App.setRoot("CinemaManagerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if ( permission == 4){//cinema manager
            try {
                pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset pickedDate if go to another screen
                App.setRoot("GeneralManagerHome.fxml");//load edit movie screen
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Filter by cinema handler
     * @param event
     */
    @FXML
    void handelCinemaPicked(ActionEvent event) {
        pickedDate=(GregorianCalendar)GregorianCalendar.getInstance();//reset the search by Date option
        ComboBox<Cinema> cinemaFilter= new ComboBox<Cinema>();
        Cinema allCinemas = new Cinema("All",null,null);
        cinemaFilter.getItems().add(allCinemas);
        for(Cinema cinema : cinemas){
            cinemaFilter.getItems().add(cinema);
        }
        cinemaFilter.setPromptText("Cinema");
        cinemaFilter.setValue(allCinemas);

        cinemaFilter.setOnAction(e ->{
            pickedCinema = cinemaFilter.getValue();

            if(pickedCinema.equals(allCinemas)){
                movies.clear();
                movies.addAll(allMovies);
                pickedCinema=null;
            }

            else{
                movies.clear();
                movies=comingSoonMovies;

                while(cinemas.isEmpty()) { Thread.yield(); }
                for (Theater t: pickedCinema.getTheaters()){
                    for(Screening s:  t.getScreeningList()){
                        if(!movies.contains(s.getMovie())){
                            movies.add(s.getMovie());
                        }
                    }
                }
            }

            //todo: load movies from this cinema only
            updateScreen();

        });

        pickedFilter.getChildren().clear();
        pickedFilter.getChildren().add(cinemaFilter);
    }

    /**
     * Filter by date handler
     * @param event
     */
    @FXML
    void handelDatePicked(ActionEvent event) {
        pickedCinema=null;//reset the search by Cinema option
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
                movies.clear();
                movies=comingSoonMovies;
                while(cinemas.isEmpty()) { Thread.yield(); }
                for(Cinema c: cinemas) {
                    for (Theater t : c.getTheaters()) {
                        for (Screening s : t.getScreeningList()) {
                            if (!movies.contains(s.getMovie()) && pickedDate.before(s.getDate())) {
                                movies.add(s.getMovie());
                            }
                        }
                    }
                }
                //todo: load movies from this cinema only
                updateScreen();
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
     * @param allMovies List
     */
    public void setMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public static List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

}