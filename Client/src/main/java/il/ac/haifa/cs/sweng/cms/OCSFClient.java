package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.ListAllMoviesRequest;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.UpdateScreeningsRequest;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.ListAllMoviesResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.UpdateScreeningsResponse;
import il.ac.haifa.cs.sweng.cms.ocsf.AbstractClient;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

/**
 * Extension of the OCSF AbstractClient class.
 *
 * @author Yuval Razieli
 */
public class OCSFClient extends AbstractClient {

    private Initializable controller;

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public OCSFClient(String host, int port) {
        super(host, port);
    }

    /**
     * Receives a response sent from the server and handles it.
     * @param msg The message received.
     */
    @Override
    protected void handleMessageFromServer(Object msg) {
        if(msg instanceof AbstractResponse) {
            handleResponse((AbstractResponse) msg);
        } else {
            // TODO: Show "Received an unexpected message from client".
        }
    }

    /**
     * Handles responses.
     * @param response Response to handle.
     */
    private void handleResponse(AbstractResponse response) {
        if(response instanceof ListAllMoviesResponse) {
            ((ManagerViewMoviesController) controller).setMovies(((ListAllMoviesResponse) response).getMovieList());
        }
        if(response instanceof UpdateScreeningsResponse) {
            // TODO: Update GUI with screenings.
        }
        // TODO: Show "Unidentified response".
    }

    /**
     * Sends a request to the server to get the list of all movies.
     */
    protected void getListOfMovies() {
        try {
            sendToServer(new ListAllMoviesRequest());
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to update a list of screenings.
     * @param screeningList New list of screenings.
     */
    protected void updateScreenings(List<Screening> screeningList) {
        try {
            sendToServer(new UpdateScreeningsRequest(screeningList));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to file a complaint.
     * @param complaint Complaint to file.
     */
    protected void fileComplaint(Complaint complaint) {
        try {
            sendToServer(new ComplaintFileRequest(complaint));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to reply to a complaint.
     * @param complaint Complaint to reply to.
     */
    protected void replyToComplaint(Complaint complaint) {
        try {
            sendToServer(new ComplaintReplyRequest(complaint));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sets the calling controller.
     * @param controller Controller which called the OCSFClient.
     */
    protected void setController(Initializable controller) {
        this.controller = controller;
    }

}
