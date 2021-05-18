/**
 * Sample Skeleton for 'ManagerViewMovies.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
            System.out.println("dddddddddddd");
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
        pic = new ImageView(movie.getPosterUrl().toString());
        pic.setFitWidth(160);
        pic.setFitHeight(220);
        pic.setImage(image);
//        pic.setId(movie.getId().toString());
//        pic.setId(id);
//        hb.getChildren().add(pic);
//        GridPane.setConstraints(pic, colIndex, rowIndex, 1, 1, HPos.CENTER, VPos.CENTER);
////         GridPane.setConstraints(pic, colIndex, rowIndex);
//        grid.getChildren().addAll(pic);

//        flow.setHgap(10);
//        flow.setVgap(10);

        VBox vb = new VBox(4, /*pic,*/ new Text(movie.getHebName()),new Text(movie.getEngName()));
        flow.getChildren().add(vb);
        FlowPane.setMargin(vb, new Insets(5,10,5,10));


/*
        pic.setOnMouseClicked(e -> {
            // System.out.printf("Mouse clicked cell [%d, %d]%n", rowIndex, colIndex);
            // System.out.println("Film Title: " + id);
            try {
                // storing the selected film to customise the newly created scene

                App.setSelectedFilmTitle(movies.get(index));
                App.setRoot("MovieEdit");
//                SceneCreator.launchScene("/scenes/ViewSelectedFilmScene.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

 */
    }

    @FXML
    void handheldsAddButton(ActionEvent event) {

    System.out.println("pushed");

    Button b2 = new Button("Information alert");


    }

    @FXML
    void handheldsBackButton(ActionEvent event) {

    }

    @FXML
    public String getId () {

        return id;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
