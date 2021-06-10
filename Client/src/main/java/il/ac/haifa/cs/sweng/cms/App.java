package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.messages.requests.LoginRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static OCSFClient ocsfClient;
    private static String host = "localhost";
    private static Integer port = 8080;
    // FIXME: Temporarily select the user type manually
    // FIXME: "Customer" or "Employee"
    private static String userType = "Employee";
    private static String firstName = "David";
    private static String username = "david_1990";
    private static String pass = "123";

    /**
     * main method that load new scene to stage
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
        Parent root = (Parent)loader.load();

        scene = new Scene(root, 640, 480);//new scene to load
        stage.setScene(scene);//set scene
        Image img = new Image(new FileInputStream("Client/src/main/resourses/icon.png"));
        stage.getIcons().add(img);


        stage.show();//show stage
        

    }

    public static int connectToServer() throws IOException {
        String hostToConnect = getHost();
        Integer portToConnect = getPort();
        ocsfClient = new OCSFClient(hostToConnect, portToConnect);
        // status:
        //  0 - Already connected
        //  1 - Succeed
        // else - Failure
        int status = ocsfClient.openConnection();
        // TODO: login request from server
        if(status == 1) {
            ocsfClient.tryLogin(username, pass);
        }
        return status;
    }

    public static void disconnect() throws IOException {
        ocsfClient.closeConnection();
    }


    static public Integer getPort() {
        return port;
    }

    static public void setPort(int p) {
        port = p;
    }

    static public String getHost() {
        return host;
    }

    static public void setHost(String h) {
        host = h;
    }

    static public String getUserType() {
        return userType;
    }

    static public void setUser(String user) {
        username = user;
    }

    static public void setPass(String password) {
        pass = password;
    }

    public static String getName() {
        return firstName;
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