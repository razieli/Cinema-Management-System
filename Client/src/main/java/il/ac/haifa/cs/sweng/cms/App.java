package il.ac.haifa.cs.sweng.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static OCSFClient ocsfClient;
//    private static Movie selectedFilmTitle ;

    /**
     * main method that lode new scene to stage
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        /*open communication withe the server*/
        ocsfClient = new OCSFClient("localhost", 8080);
        ocsfClient.openConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMovies.fxml"));//FXML to load
        Parent root = (Parent)loader.load();

        scene = new Scene(root, 640, 480);//new sean to load
        stage.setScene(scene);//set scene
        stage.show();//show stage

    }

    /**
     * set new FXML to to show on screen
     * @param fxml
     * @throws IOException
     */
    static void setRoot(String fxml) throws  IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));//FXML to load
        Parent root = (Parent)loader.load(); //load FXML to root object
        scene.setRoot(root);//set new root to scene
    }

    /**
     * main method calls start method to start rolling
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Sets the calling controller and returns the OCSFClient instance.
     * @return OCSFClient instance.
     */
    protected static OCSFClient getOcsfClient(Initializable controller) {
        ocsfClient.setController(controller);
        return ocsfClient;
    }

}