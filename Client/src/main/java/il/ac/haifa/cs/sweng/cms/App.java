//package il.ac.haifa.cs.sweng.cms;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
///**
// * JavaFX App
// */
//public class App extends Application {
//    private static Scene scene;
//
//    public static Parent root;
//    public static  Stage primaryStage;
//    private boolean mode = false;
//    public static String selectedFilmTitle = "";
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        scene = new Scene(loadFXML("ViewMovies"));
//        stage.setScene(scene);
//        stage.show();
//    }
//
////    static void setRoot(String fxml) throws IOException {
////        scene.setRoot(loadFXML(fxml));
////    }
//
//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        return fxmlLoader.load();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//
//    public static String getPath() {
//
//        String path = ClassLoader.getSystemClassLoader().getResource(".").getPath();
//
//        // leave this here to run in Eclipse... if proper deployment then
//        // remove code to only run from jar file
////        if (path.contains("zcinema/bin"))
////            path = path.split("zcinema")[0];
//
//        return path;
//    }
//
//    static void setSelectedFilmTitle(String selectedFilmTitle) {
//
//        App.selectedFilmTitle = selectedFilmTitle;
//    }
//
//    static Parent getRoot() {
//
//        return root;
//    }
//
//    static void setRoot(Parent root) {
//
//        App.root = root;
//    }
//
//    static Stage getStage() {
//
//        return primaryStage;
//    }
//
//    static void setStage(Stage stage) {
//
//        App.primaryStage = stage;
//    }
//
//
//    static String getSelectedFilmTitle() {
//
//        return selectedFilmTitle;
//    }
//
//
//    public boolean isMode() {
//        return mode;
//    }
//
//    public void setMode(boolean mode) {
//        this.mode = mode;
//    }
//}

package il.ac.haifa.cs.sweng.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static Movie selectedFilmTitle ;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ViewMovies"), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Movie getSelectedFilmTitle() {
        return selectedFilmTitle;
    }

    public static void setSelectedFilmTitle(Movie selectedFilmTitle) {
        App.selectedFilmTitle = selectedFilmTitle;
    }

    public static void main(String[] args) {
        DB.main(args);
        launch();
    }

}

