package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
//    private static Movie selectedFilmTitle ;




    @Override
    public void start(Stage stage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("ManagerViewMovies.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerViewMovies.fxml"));
        Parent root = (Parent)loader.load();

         scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws  IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        Parent root = (Parent)loader.load();
        scene.setRoot(root);
    }

//    public static Movie getSelectedFilmTitle() {
//        return selectedFilmTitle;
//    }
//
//    public static void setSelectedFilmTitle(Movie selectedFilmTitle) {
//        App.selectedFilmTitle = selectedFilmTitle;
//    }

    public static void main(String[] args) {
        launch();
    }

}