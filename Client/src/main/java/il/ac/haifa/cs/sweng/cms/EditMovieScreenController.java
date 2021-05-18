/**
 * Sample Skeleton for 'EditMovieScreen.fxml' Controller Class
 */

package il.ac.haifa.cs.sweng.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;

public class EditMovieScreenController {

    @FXML // fx:id="genreHeader"
    private Text genreHeader; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="imageBox"
    private TextField imageBox; // Value injected by FXMLLoader

    @FXML // fx:id="trailerBox"
    private TextField trailerBox; // Value injected by FXMLLoader

    @FXML // fx:id="englishTitle"
    private TextField englishTitle; // Value injected by FXMLLoader

    @FXML // fx:id="hebrewTitle"
    private TextField hebrewTitle; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionBox"
    private TextArea descriptionBox; // Value injected by FXMLLoader

    @FXML // fx:id="yearBox"
    private TextField yearBox; // Value injected by FXMLLoader

    @FXML // fx:id="availableBox"
    private TextField availableBox; // Value injected by FXMLLoader

    @FXML // fx:id="castBox"
    private TextArea castBox; // Value injected by FXMLLoader

    @FXML // fx:id="directorBox"
    private TextField directorBox; // Value injected by FXMLLoader

    @FXML // fx:id="screeningBox"
    private TextArea screeningBox; // Value injected by FXMLLoader

    @FXML // fx:id="screeningControlBox"
    private ComboBox<?> screeningControlBox; // Value injected by FXMLLoader

    @FXML // fx:id="deleteLastButton"
    private Button deleteLastButton; // Value injected by FXMLLoader

    @FXML // fx:id="saveChangesButton"
    private Button saveChangesButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="inputEngTitle"
    private Text inputEngTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputHebTitle"
    private Text inputHebTitle; // Value injected by FXMLLoader

    @FXML // fx:id="inputImage"
    private ImageView inputImage; // Value injected by FXMLLoader

    @FXML // fx:id="inputTrailer"
    private Hyperlink inputTrailer; // Value injected by FXMLLoader

    @FXML // fx:id="inputLength"
    private Text inputLength; // Value injected by FXMLLoader

    @FXML // fx:id="inputYear"
    private Text inputYear; // Value injected by FXMLLoader

    @FXML // fx:id="inputDirector"
    private Text inputDirector; // Value injected by FXMLLoader

    @FXML // fx:id="inputCast"
    private Text inputCast; // Value injected by FXMLLoader

    @FXML // fx:id="inputDescription"
    private Text inputDescription; // Value injected by FXMLLoader

    @FXML
    void handheldsAviabilety(InputMethodEvent event) {

    }

    @FXML
    void handheldsBackButton(ActionEvent event) {

    }

    @FXML
    void handheldsCast(InputMethodEvent event) {

    }

    @FXML
    void handheldsDeleteButton(ActionEvent event) {

    }

    @FXML
    void handheldsDeleteLastButton(ActionEvent event) {

    }

    @FXML
    void handheldsDescription(InputMethodEvent event) {

    }

    @FXML
    void handheldsDirector(InputMethodEvent event) {

    }

    @FXML
    void handheldsEnglishName(InputMethodEvent event) {

    }

    @FXML
    void handheldsHeberwName(InputMethodEvent event) {

    }

    @FXML
    void handheldsImageURL(InputMethodEvent event) {

    }

    @FXML
    void handheldsSaveChangesButton(ActionEvent event) {

    }

    @FXML
    void handheldsScreeningChack(ActionEvent event) {

    }

    @FXML
    void handheldsTrailerURL(InputMethodEvent event) {

    }

    @FXML
    void handheldsYear(InputMethodEvent event) {

    }

}
