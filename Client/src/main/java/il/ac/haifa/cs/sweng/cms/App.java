package il.ac.haifa.cs.sweng.cms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
//    private static Movie selectedFilmTitle ;


    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

<<<<<<< Updated upstream
        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
=======
         scene = new Scene(root, 640, 480);
>>>>>>> Stashed changes
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