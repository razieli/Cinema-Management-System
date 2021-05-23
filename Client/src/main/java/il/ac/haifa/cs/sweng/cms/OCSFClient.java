package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.ListAllMoviesRequest;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.UpdateScreeningsRequest;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.ListAllMoviesResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.UpdateScreeningsResponse;
import il.ac.haifa.cs.sweng.cms.ocsf.AbstractClient;

import java.io.IOException;
import java.util.List;

/**
 * Extension of the OCSF AbstractClient class.
 *
 * @author Yuval Razieli
 */
public class OCSFClient extends AbstractClient {

    private ManagerViewMoviesController controller;

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public OCSFClient(String host, int port, ManagerViewMoviesController controller) {
        super(host, port);
        this.controller = controller;
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
            // TODO: Update GUI with movies.
            controller.setMovies(((ListAllMoviesResponse) response).getMovieList());
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

}
