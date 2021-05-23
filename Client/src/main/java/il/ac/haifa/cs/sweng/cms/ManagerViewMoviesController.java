/**
 * Sample Skeleton for 'ManagerViewMovies.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerViewMoviesController implements Initializable {

    Scene scene;
    ArrayList<File> fileList = new ArrayList<File>();
    List<Movie> movies= new ArrayList<Movie>();
    HBox hb = new HBox();

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

//    @FXML // fx:id="grid"
//    private GridPane grid; // Value injected by FXMLLoader

    @FXML // fx:id="flow"
    private FlowPane flow; // Value injected by FXMLLoader

    @FXML // fx:id="genreHeader"
    private Text genreHeader; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="filterMenu"
    private MenuButton filterMenu; // Value injected by FXMLLoader

    @FXML
    ImageView pic;
    @FXML
    Image image;
    @FXML
    String id;

@Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            OCSFClient ocsfClient = new OCSFClient("localhost", 8080, this);
            ocsfClient.openConnection();
            ocsfClient.getListOfMovies();
            //movies = Init.getAllMovies();

            URI searchButtonUri = new URI("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbws1761LoKcQ68sQeqmJNXr2WhuEE5SEVY1DmKK6mka1o8FpbeEmTj_cBWfRR1ksDbAc&usqp=CAU");
            ImageView searchButtonIm = new ImageView(searchButtonUri.toString());
            searchButtonIm.setPreserveRatio(true);
            searchButtonIm.setFitHeight(searchButton.getPrefHeight());
            searchButtonIm.setFitWidth(searchButton.getPrefWidth());
            searchButton.setGraphic(searchButtonIm);

            URI backButtonUri = new URI("https://cdn.pixabay.com/photo/2016/09/05/10/50/app-1646213_640.png");
            ImageView backButtonIm = new ImageView(backButtonUri.toString());
            backButtonIm.setPreserveRatio(true);
            backButtonIm.setFitHeight(backButton.getPrefHeight());
            backButtonIm.setFitWidth(backButton.getPrefWidth());
            backButton.setGraphic(backButtonIm);


            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);



//            // gridpane settings
//            // setting exterior grid padding
//            grid.setPadding(new Insets(7, 7, 7, 7));
//            // setting interior grid padding
//            grid.setHgap(10);
//            grid.setVgap(10);
//            grid.setGridLinesVisible(true);

            // gridpane settings
            // setting exterior grid padding
//            flow.setPadding(new Insets(7, 7, 7, 7));
//            // setting interior grid padding
//            flow.setHgap(10);
//            flow.setVgap(10);
//            flow.setGridLinesVisible(true);

//            int rows = (movies.size() / 4) + 1;
//            int columns = 4;
while(movies.isEmpty()) { Thread.yield(); }
            for (Movie movie : movies) {
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < columns; j++) {
//                        addImage(movie.getId() /*, j, i*/);
                        addImage(movie);
//                        continue;
                    }
//                }
//            }

        }

        catch(Exception e){
//            System.out.println("dddddddddddd");
        e.printStackTrace();
    }

}

//    private void addImage(int index, int colIndex, int rowIndex) {
    protected void addImage(Movie movie){

//        String idToCut = fileList.get(index).getName();
//        String id = idToCut.substring(0, (idToCut.length() - 4));
        // System.out.println(id);
        // System.out.println(fileList.get(i).getName());
//        id=movies.get(index).getEngName();
//        image = new Image(movies.get(index).getPosterUrl());
//        image = new Image(movies.get(index).getPosterUrl());
//        System.out.println("helooooooooooooooooo"+movies.get(index).getPosterUrl().toString());

//                pic = new ImageView(movie.getPosterUrl().toString());
//        pic.setFitWidth(160);
//        pic.setFitHeight(220);


        //pic.setImage(image);
//        pic.setId(movie.getId().toString());
//        pic.setId(id);
//        hb.getChildren().add(pic);
//        GridPane.setConstraints(pic, colIndex, rowIndex, 1, 1, HPos.CENTER, VPos.CENTER);
////         GridPane.setConstraints(pic, colIndex, rowIndex);
//        grid.getChildren().addAll(pic);

//        flow.setHgap(10);
//        flow.setVgap(10);


        SimpleDateFormat format= new SimpleDateFormat ("HH:mm ");
        String screenTime="";
        for(Screening screen:movie.getScreening()){
            screenTime+= format.format(screen.getDate().getTime()).toString() ;
        }
        pic = new ImageView(movie.getPosterUrl().toString());
        pic.setFitWidth(160);
        pic.setFitHeight(220);
        Text textHebName = new Text(movie.getHebName());
        textHebName.setFill(Color.ORANGE);
        textHebName.setFont(Font.font(null, FontWeight.BOLD, 12));
        Text textEngName = new Text(movie.getEngName());
        textEngName.setFill(Color.ORANGE);
        textEngName.setFont(Font.font(null, FontWeight.BOLD, 12));
        Text textScreenTime = new Text(screenTime);
        textScreenTime.setFill(Color.ORANGE);

        VBox vb = new VBox(4, pic, textHebName, textEngName, textScreenTime);

        flow.getChildren().add(vb);
        FlowPane.setMargin(vb, new Insets(5,30,5,10));


        vb.setOnMouseClicked(e -> {
            // System.out.printf("Mouse clicked cell [%d, %d]%n", rowIndex, colIndex);
//            System.out.println("Film Title: " + movie.getEngName());
            try {
                // storing the selected film to customise the newly created scene

                EditMovieScreenController.setSelectedFilmTitle(movie);
                App.setRoot("EditMovieScreen.fxml");
//                SceneCreator.launchScene("/scenes/ViewSelectedFilmScene.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    @FXML
    void handheldsBackButton(ActionEvent event) {

    }

    @FXML
    void handheldsFilterMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("New features coming soon..  :)");
        alert.showAndWait();
    }

    @FXML
    void handheldsSearchButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("New features coming soon..  :)");
        alert.showAndWait();
    }

    @FXML
    public String getId () {

        return id;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
